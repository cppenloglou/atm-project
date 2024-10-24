package org.vaadin.example.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.vaadin.example.MyWebClient;
import org.vaadin.example.RegisterRequest;
import org.vaadin.example.SecurityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("register")
@Component
public class RegisterView extends VerticalLayout implements BeforeEnterObserver{

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final EmailField emailField = new EmailField("Email");
    private final PasswordField passwordField = new PasswordField("Password");
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        VaadinSession.getCurrent().setAttribute("access_token", null);
        // Notify user and redirect to login
        Notification.show("If you were logged in, now you have been logged out.", 3000, Notification.Position.BOTTOM_START);
    }

    public RegisterView(MyWebClient myWebClient) {
        // Title
        H1 title = new H1("Register Account");
        
        // Form Fields
        firstNameField.setRequired(true);
        lastNameField.setRequired(true);
        emailField.setRequired(true);
        passwordField.setRequired(true);

        // Register Button
        Button registerButton = new Button("Register");
        registerButton.addClickListener(e -> {
            
            var request = createRegisterRequest(
                firstNameField.getValue(), 
                lastNameField.getValue(),
                emailField.getValue(),
                passwordField.getValue());
            
            List<Object> result =  validateRegistration(myWebClient, request);
            if ((boolean) result.get(0)) {
                Notification.show((String) result.get(1), 6000, Notification.Position.TOP_CENTER);
                registerButton.getUI().ifPresent(ui -> ui.navigate("/login"));
            } else {
                Notification.show((String) result.get(1), 6000, Notification.Position.TOP_CENTER);
            }
        });

        // Layout setup
        setAlignItems(Alignment.CENTER); // Center everything horizontally
        setJustifyContentMode(JustifyContentMode.CENTER); // Center everything vertically
        add(title, firstNameField, lastNameField, emailField, passwordField, registerButton);
    }

    private RegisterRequest createRegisterRequest(String firstName, String lastName, String email, String password){
        return RegisterRequest.builder()
        .email(email)
        .firstname(firstName)
        .lastname(lastName)
        .password(password)
        .role("USER")
        .build();
    }
    
    private List<Object> validateRegistration(MyWebClient myWebClient,  RegisterRequest request) {
        

        List<Object> responseList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try{
            String response = myWebClient.register(request);
            logger.info(response);
            
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

            // If you want to convert it to a Map later
            Map<String, String> tokenMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                tokenMap.put(key, jsonObject.get(key).getAsString());
            }
            responseList.add(true);
            responseList.add("Registration successful!");
            responseList.add(tokenMap.get("access_token"));

        } catch (RuntimeException e) {

            JsonObject jsonObject = gson.fromJson(e.getMessage().strip(), JsonObject.class);

            // If you want to convert it to a Map later
            Map<String, String> errorMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                errorMap.put(key, jsonObject.get(key).getAsString());
            }

            StringJoiner errorJoiner = new StringJoiner("\n");
            errorMap.forEach((key, value) -> errorJoiner.add(key + ": " + value));
            responseList.add(false);
            responseList.add(errorJoiner.toString());
        }

        return responseList;
    }
}

