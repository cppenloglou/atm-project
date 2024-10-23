package org.vaadin.example;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView() {
        // Title
        H1 title = new H1("ATM Login");
        
        // Login Form
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> {
            // Handle login attempt (you can integrate this with Spring Security)
            String username = e.getUsername();
            String password = e.getPassword();
            
            if (authenticate(username, password)) {
                Notification.show("Login successful!", 3000, Notification.Position.TOP_CENTER);
                // Redirect to dashboard or another page
            } else {
                Notification.show("Invalid credentials. Please try again.", 3000, Notification.Position.TOP_CENTER);
                loginForm.setError(true); // Show an error message in the form
            }
        });
        
        // Layout setup
        setAlignItems(Alignment.CENTER); // Center everything
        setJustifyContentMode(JustifyContentMode.CENTER); // Center vertically
        add(title, loginForm);
    }

    // Simple authentication method (for demonstration)
    private boolean authenticate(String username, String password) {
        
        return "user".equals(username) && "password".equals(password);
    }
}