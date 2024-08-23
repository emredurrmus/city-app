package com.library.meetapp.pattern;

import com.library.meetapp.component.UploadField;
import com.library.meetapp.entity.Notification;
import com.library.meetapp.entity.User;
import com.library.meetapp.navigation.ui.PostUtil;
import com.library.meetapp.notification.component.NotificationCard;
import com.library.meetapp.service.NotificationService;
import com.library.meetapp.util.AppCtx;
import com.library.meetapp.util.JsonUtil;
import com.library.meetapp.util.StringUtils;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinServlet;

import java.util.List;

public class TopToolbar extends HorizontalLayout {

    private Avatar avatar;
    private Image language;
    private Span username;
    private Button logout;
    private Button notification;
    private Span newNotificationCount;
    private ContextMenu userContextMenu;
    private ContextMenu notificationContextMenu;

    private User currentUser = AppCtx.getBean(User.class);
    private NotificationService notificationService = AppCtx.getBean(NotificationService.class);

    private List<Notification> notifications;

    public TopToolbar() {
        setClassName("top-toolbar");

        String[] nameAndPath = JsonUtil.getFileNameAndPath(currentUser.getImagePath());

        avatar = new Avatar();
        if(nameAndPath != null) {
            avatar.setImageResource(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]));
        }
        userContextMenu = new ContextMenu(avatar);
        userContextMenu.setOpenOnClick(true);
        userContextMenu.addItem(UIUtil.labelWithIcon(VaadinIcon.LOCK, "Kullanıcı Ayarları", "lang-item"), evt -> navigateSettings());

        username = new Span();
        username.setClassName("username");
        logout = UIUtil.createTertiaryInlineButton(VaadinIcon.SIGN_OUT, "Çıkış", ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        logout.addClickListener(evt -> getUI().ifPresent(ui -> ui.getPage().setLocation(generateLogoutUrl())));
        userContextMenu.addItem(UIUtil.createTertiaryInlineButton(VaadinIcon.SIGN_OUT, "Çıkış", ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL), evt -> getUI().ifPresent(ui -> ui.getPage().setLocation(generateLogoutUrl())));

        notification = UIUtil.createTertiaryButton(VaadinIcon.BELL);
        notification.addClassName("notification-button");
        newNotificationCount = new Span("");
        newNotificationCount.addClassNames("badge", "red-badge", "notification-alert");
        newNotificationCount.setVisible(false);
        notification.getElement().appendChild(newNotificationCount.getElement());

        notifications = notificationService.getNotifications(currentUser);
        updateNotificationCount(notifications.size());

        notificationContextMenu = new ContextMenu(notification);
        notificationContextMenu.setOpenOnClick(true);
        notificationContextMenu.addItem(UIUtil.labelWithIcon(VaadinIcon.BELL, "Bildirimler", "lang-item"));
        notificationContextMenu.setClassName("notification-context");

        notification.addClickListener(evt -> {
            notificationContextMenu.removeAll();
            notificationContextMenu.add(createNotificationList(notifications));
            notificationContextMenu.isOpened();
        });

        add(notification, avatar);
    }

    private void updateNotificationCount(int count) {
        newNotificationCount.setText(Integer.toString(count));
        newNotificationCount.setVisible(count > 0);
    }

    private Component createNotificationList(List<Notification> notifications) {
        VerticalLayout notificationList = new VerticalLayout();
        notificationList.setPadding(false);
        notificationList.setSpacing(false);

        for (Notification notification : notifications) {
            notificationList.add(NotificationCard.create(notification, this::navigatePostDetail));
        }

        return notificationList;
    }

    private String generateLogoutUrl() {
        final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
        return contextPath + "/logout";
    }

    public void setUser(User user) {
        currentUser = user;
        avatar.setName(StringUtils.initials(user.getFirstName(), user.getLastName()));
        username.setText(StringUtils.concat(user.getFirstName(), user.getLastName()));
    }

    private void navigateSettings() {
        UI.getCurrent().navigate("settings");
    }

    private void navigatePostDetail(Notification notification) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, notification.getPost()));
        notificationContextMenu.remove();
    }
}
