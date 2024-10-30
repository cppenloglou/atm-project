package org.vaadin.example.views;

import java.util.Map;

import org.vaadin.example.MyWebClient;
import org.vaadin.example.SecurityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("/dashboard")
public class DashboardView extends AppLayout implements BeforeEnterObserver {
    
    private VerticalLayout mainContent;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SecurityUtils.isAuthenticated()) {
            event.forwardTo("/");// Prevent entering the protected view
        }
    }

    public DashboardView(MyWebClient myWebClient) {
        createHeader();
        createNavigation();
        createMainContent();
        showDashboardContent(myWebClient);
    }

    private void createHeader() {
        H1 title = new H1("My ATM App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "10px 10px 10px 10px")
                .set("position", "absolute");

        addToNavbar(title);
    }

    private void createNavigation() {
        HorizontalLayout navigation = getNavigation();
        addToNavbar(navigation);
    }

    private void createMainContent() {
        mainContent = new VerticalLayout();
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setClassName("centered-content");
        setContent(mainContent);
    }

    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL, LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL);

        // Links
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        dashboardLink = createLink(dashboardLink);

        RouterLink withdrawalLink = new RouterLink("Withdrawal", WithdrawalView.class);
        withdrawalLink = createLink(withdrawalLink);
        
        RouterLink depositLink = new RouterLink("Deposit", DepositView.class);
        depositLink = createLink(depositLink);

        Button logouButton = new Button("Logout", e -> handleLogout());

        navigation.add(dashboardLink, withdrawalLink, depositLink, logouButton);
        return navigation;
    }

    private void handleLogout() {
        // Save the token in the session
        VaadinSession.getCurrent().setAttribute("access_token", null);

        // Notify user and redirect
        Notification.show("Logout successful!", 3000, Notification.Position.BOTTOM_START);
        getUI().ifPresent(ui -> ui.navigate("/")); // Redirect to protected area
    }

    private RouterLink createLink(RouterLink link) {

        link.addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.TextColor.SECONDARY, LumoUtility.FontWeight.MEDIUM);
        link.getStyle().set("text-decoration", "none");

        return link;
    }

    // Methods to Update Main Content Based on Action

    private void showDashboardContent(MyWebClient myWebClient) {
        Gson gson = new GsonBuilder().create();
        mainContent.removeAll();
        var user = VaadinSession.getCurrent().getAttribute("user");
        if (user == null) {
            return;
        }
        Map<String, String> userMap = (Map<String, String>) user;
        H3 firstname = new H3("Firstname: " + userMap.get("firstname"));
        H3 lastname = new H3("Lastname: " + userMap.get("lastname"));
        H3 email = new H3("Email: " + userMap.get("email"));
        String id = userMap.get("id");
        var token = VaadinSession.getCurrent().getAttribute("access_token");
        var accountrequest = myWebClient.getAllAccountsForUser(id, token.toString());
        JsonObject account = gson.fromJson(accountrequest, JsonObject.class);
        VaadinSession.getCurrent().setAttribute("account", account);
        H3 balanceInfo = new H3("Balance: $"+ account.get("balance").getAsString()); // Example balance
        mainContent.add(firstname, lastname, email, balanceInfo);
    }
}
