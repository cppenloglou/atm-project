package org.vaadin.example;

import com.vaadin.flow.server.VaadinSession;

public class SecurityUtils {

    public static boolean isAuthenticated() {
        return VaadinSession.getCurrent().getAttribute("access_token") != null;
    }
    
}
