package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends VerticalLayout {

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final EmailField emailField = new EmailField("Email");
    private final PasswordField passwordField = new PasswordField("Password");

    private final Binder<RegisterRequest> binder = new Binder<>(RegisterRequest.class);
    
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
            String firstName = firstNameField.getValue();
            String lastName = lastNameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            List<Object> result =  validateRegistration(myWebClient, firstName, lastName, email, password);
            if ((boolean) result.get(0)) {
                Notification.show((String) result.get(1), 6000, Notification.Position.TOP_CENTER);
                resetFieldStyles();
                registerButton.getUI().ifPresent(ui -> ui.navigate("/login"));
            } else {
                Notification.show((String) result.get(1), 6000, Notification.Position.TOP_CENTER);
                changeFieldStyles();
            }
        });

        // Layout setup
        setAlignItems(Alignment.CENTER); // Center everything horizontally
        setJustifyContentMode(JustifyContentMode.CENTER); // Center everything vertically
        add(title, firstNameField, lastNameField, emailField, passwordField, registerButton);
    }

    private void resetFieldStyles() {
        firstNameField.removeClassName("invalid");
        lastNameField.removeClassName("invalid");
        emailField.removeClassName("invalid");
        passwordField.removeClassName("invalid");
    }

    private void changeFieldStyles() {
        if (!binder.validate().isOk()) {
            // If fields are invalid, add the invalid class
            firstNameField.addClassName("invalid");
            lastNameField.addClassName("invalid");
            emailField.addClassName("invalid");
            passwordField.addClassName("invalid");
        }
    }

    // Basic validation for demonstration purposes
    private List<Object> validateRegistration(MyWebClient myWebClient,  String firstName, String lastName, String email, String password) {
        var request = RegisterRequest
        .builder()
        .email(email)
        .firstname(firstName)
        .lastname(lastName)
        .password(password)
        .role("USER")
        .build();

        List<Object> responseList = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try{
            String response = myWebClient.register(request);
            System.out.println(response);
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

            // If you want to convert it to a Map later
            Map<String, String> tokenMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                tokenMap.put(key, jsonObject.get(key).getAsString());
            }
            responseList.add(true);
            responseList.add("Registration successful!");
            responseList.add(tokenMap.get(""));
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

