package org.vaadin.example;



import com.vaadin.flow.server.SessionDestroyEvent;
import com.vaadin.flow.server.SessionDestroyListener;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.VaadinService;

public class MyAppInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(com.vaadin.flow.server.ServiceInitEvent event) {
        VaadinSession.getCurrent().addSessionDestroyListener(new SessionDestroyListener() {
            @Override
            public void sessionDestroy(SessionDestroyEvent event) {
                // Clear token on session destroy
                event.getSession().setAttribute("authToken", null);
            }
        });
    }
}
