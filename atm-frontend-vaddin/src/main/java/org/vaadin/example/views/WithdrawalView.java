package org.vaadin.example.views;

import com.google.gson.GsonBuilder;
import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Transaction;
import org.vaadin.example.MyWebClient;
import org.vaadin.example.SecurityUtils;
import org.vaadin.example.TransactionRequest;
import org.vaadin.example.TransactionType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.hilla.parser.jackson.JacksonObjectMapperFactory.Json;

@Route("/withdrawal")
public class WithdrawalView extends AppLayout implements BeforeEnterObserver {
    private final Logger logger = Logger.getLogger(getClass().getName());


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SecurityUtils.isAuthenticated()) {
            event.forwardTo("/");// Prevent entering the protected view
        }
    }
    private VerticalLayout mainContent;
    public WithdrawalView(MyWebClient myWebClient) {
        createHeader();
        createNavigation();
        createMainContent();
        showWithdrawalContent(myWebClient);
    }

    private void showWithdrawalContent(MyWebClient myWebClient) {
        mainContent.removeAll();
        NumberField amountOfWithdraw = new NumberField("Ammount to withdraw"); // Example user information
        Button withdrawButton = new Button("Withdraw", e -> handleWithdraw(myWebClient, amountOfWithdraw.getValue())); // Example balance
        mainContent.add(amountOfWithdraw, withdrawButton);
    }

    private void handleWithdraw(MyWebClient myWebClient, Double amount) {
        var account = VaadinSession.getCurrent().getAttribute("account");
        JsonObject accountMap = (JsonObject) account;
        var token = VaadinSession.getCurrent().getAttribute("access_token");

        TransactionRequest transactionRequest = TransactionRequest.builder()
        .accountId(accountMap.get("id").getAsInt())
        .amount(BigDecimal.valueOf(amount))
        .type(TransactionType.WITHDRAWAL).build();


        logger.info(transactionRequest.toString());
        Gson gson = new GsonBuilder().create();
        try{
            logger.info(myWebClient.createTransaction(transactionRequest, token.toString()));
            Notification.show("Withdrawal successful!", 3000, Notification.Position.TOP_CENTER);
        } catch(Exception e){
            JsonObject jsonObject = gson.fromJson(e.getMessage().strip(), JsonObject.class);

            Notification.show(jsonObject.get("error").getAsString(), 3000, Notification.Position.TOP_CENTER);
        }
       
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
}
