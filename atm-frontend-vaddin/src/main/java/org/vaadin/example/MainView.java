package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/my-custom.css")
public class MainView extends HorizontalLayout {

    public MainView() {
        setSizeFull(); // Make sure the layout takes the full size of the window

        // Left Side - Image
        Image atmImage = new Image("images/digital-art-with-planet-earth.jpg", "ATM Image");
        atmImage.setSizeFull(); // Make image take full space

        Div imageContainer = new Div(atmImage);
        imageContainer.setWidth("100%"); // 70% width for the image
        imageContainer.getStyle().set("overflow", "hidden"); // Ensures the image scales nicely

        // Right Side - Dark Background with content
        VerticalLayout rightSide = new VerticalLayout();
        rightSide.setWidth("30%");
        rightSide.getStyle().set("background-color", "#2e2e2e"); // Dark background
        rightSide.setPadding(true);
        rightSide.setSpacing(true);
        rightSide.setAlignItems(Alignment.CENTER); // Center align the content

        // Title
        H1 title = new H1("Hello from ATM");
        title.getStyle().set("color", "#fff"); // White text
        title.addClassName("h1");

        // Buttons
        Button loginButton = new Button("Login");
        loginButton.addClickListener(e -> 
            loginButton.getUI().ifPresent(ui -> ui.navigate("login"))
        );

        Button registerButton = new Button("Register");
        registerButton.addClickListener(e -> 
            registerButton.getUI().ifPresent(ui -> ui.navigate("register"))
        );

        loginButton.addClassNames("button button:hover");
        registerButton.addClassNames("button button:hover");

        // Footer with GitHub link
        Anchor githubLink = new Anchor("https://github.com/cppenloglou", "Visit my GitHub");
        githubLink.setTarget("_blank");
        githubLink.getStyle().set("color", "#fff"); // White text
        githubLink.addClassName("a");

        // Styling for buttons (optional, you can further refine the style using CSS)
        loginButton.getStyle().set("margin", "10px");
        registerButton.getStyle().set("margin", "10px");

        // Add components to the right-side layout
        rightSide.add(title, loginButton, registerButton, githubLink);

        addClassName("login-rich-content");

        // Add the two sides to the main layout
        add(imageContainer, rightSide);
    }
}
