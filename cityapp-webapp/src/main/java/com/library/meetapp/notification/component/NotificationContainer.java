package com.library.meetapp.notification.component;

import com.library.meetapp.entity.Notification;
import com.library.meetapp.entity.User;
import com.library.meetapp.navigation.ui.PostUtil;
import com.library.meetapp.post.component.PostCard;
import com.library.meetapp.service.NotificationService;
import com.library.meetapp.util.AppCtx;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public class NotificationContainer extends VerticalLayout {

    private Div header;
    private Tabs tabs;
    private VirtualList<Notification> grid;
    private Button closeDrawer;

    private Tab unreadTab;
    private Tab readTab;

    private final NotificationService service;
    private final User loginUser;

    public NotificationContainer(String title) {
        setPadding(false);
        setSpacing(false);

        service = AppCtx.getBean(NotificationService.class);
        loginUser = AppCtx.getBean(User.class);

        setClassName("notification-wrapper");

        closeDrawer = UIUtil.createTertiaryButton(VaadinIcon.CLOSE);
        closeDrawer.addClickListener(evt -> close());
        tabs = new Tabs();
        tabs.setWidthFull();
        header = new Div(new H3(title), closeDrawer);
        header.setClassName("notification-header");
        grid = new VirtualList<>();
        grid.setRenderer(new ComponentRenderer<>(t -> NotificationCard.create(t, this::processNotificationClick)));

        unreadTab = new Tab(getTranslation("notifications.unread"));
        readTab = new Tab(getTranslation("notifications.read"));

        tabs.add(unreadTab, readTab);
//        tabs.addSelectedChangeListener(event -> showNotifications());

        add(header, tabs, new VerticalLayout(grid));

//        showNotifications();
    }

//    private void showNotifications() {
//        if (tabs.getSelectedTab() == readTab) {
//            grid.setItems(service.getPasiveNotifications(loginUser));
//        } else {
//            grid.setItems(service.getNotifications(loginUser));
//        }
//    }

    //    private void markAsRead(Notification notification) {
//        service.markAsRead(notification, loginUser);
//        showNotifications();
//    }
//
    private void processNotificationClick(Notification notification) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, notification.getPost()));
        close();
    }

    public void close() {
        getParent().ifPresent(parent -> parent.getElement().removeAttribute("open"));
//        getParent().ifPresent(parent -> parent.getElement().setAttribute("open", false));
    }

}
