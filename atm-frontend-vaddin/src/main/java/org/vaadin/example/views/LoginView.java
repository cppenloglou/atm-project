package org.vaadin.example.views;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Logger;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.vaadin.example.AuthenticationRequest;
import org.vaadin.example.MyWebClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
@Component
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Clear token
        VaadinSession.getCurrent().setAttribute("access_token", null);

        // Show notification only if the previous view was not the main view
        Notification.show("If you were logged in, now you have been logged out.", 3000, Notification.Position.BOTTOM_START);
    }
    

    private final Logger logger = Logger.getLogger(getClass().getName());

    public LoginView(MyWebClient myWebClient) {
        // Title
        H1 title = new H1("ATM Login");
        
        // Login Form
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> {
            // Handle login attempt (you can integrate this with Spring Security)
            String username = e.getUsername();
            String password = e.getPassword();
            
            if (authenticate(myWebClient, username, password)) {
               loginForm.getUI().ifPresent(ui -> ui.navigate("dashboard"));
            } else {
                
                loginForm.setError(true); // Show an error message in the form
            }
        });
        
        // Layout setup
        setAlignItems(Alignment.CENTER); // Center everything
        setJustifyContentMode(JustifyContentMode.CENTER); // Center vertically
        add(title, loginForm);
    }

    // Simple authentication method (for demonstration)
    private boolean authenticate(MyWebClient myWebClient, String username, String password) {
        var request = AuthenticationRequest.builder()
                    .email(username)
                    .password(password)
                    .build();
        
        Gson gson = new GsonBuilder().create();
        try{
            String response = myWebClient.authenticate(request);
            logger.info(response);
            
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

            // If you want to convert it to a Map later
            Map<String, String> tokenMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                tokenMap.put(key, jsonObject.get(key).getAsString());
            }

            VaadinSession.getCurrent().setAttribute("access_token", tokenMap.get("access_token"));
            var userResponse = myWebClient.getUserByToken(tokenMap.get("access_token"));
            JsonObject user = gson.fromJson(userResponse, JsonObject.class);

            // If you want to convert it to a Map later
            Map<String, String> userMap = new HashMap<>();
            for (String key : user.keySet()) {
                userMap.put(key, user.get(key).getAsString());
            }
            VaadinSession.getCurrent().setAttribute("user", userMap);

            logger.info(userMap.toString());
            logger.info(userResponse);

            Notification.show("Login successful!", 3000, Notification.Position.TOP_CENTER);
            return true;

        } catch (RuntimeException e) {

            JsonObject jsonObject = gson.fromJson(e.getMessage().strip(), JsonObject.class);

            // If you want to convert it to a Map later
            Map<String, String> errorMap = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                errorMap.put(key, jsonObject.get(key).getAsString());
            }

            StringJoiner errorJoiner = new StringJoiner("\n");
            errorMap.forEach((key, value) -> errorJoiner.add(key + ": " + value));

            Notification.show(errorJoiner.toString(), 3000, Notification.Position.TOP_CENTER);
            return false;
        }
    }
}