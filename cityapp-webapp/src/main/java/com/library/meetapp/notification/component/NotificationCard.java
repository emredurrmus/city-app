package com.library.meetapp.notification.component;

import com.library.meetapp.component.UploadField;
import com.library.meetapp.entity.Notification;
import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import com.library.meetapp.post.component.PostCard;
import com.library.meetapp.util.DateUtil;
import com.library.meetapp.util.JsonUtil;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableConsumer;

public class NotificationCard extends VerticalLayout {

    private Image image;


    public static NotificationCard create(Notification notification, SerializableConsumer<Notification> detailAction) {
        return new NotificationCard(notification,detailAction);
    }


    public NotificationCard(Notification notification, SerializableConsumer<Notification> detailAction) {
        setSpacing(false);
        setClassName("notification-card");
        Span date = new Span(DateUtil.format(notification.getCreatedAt(), DateUtil.USERFRIENDLY_DATETIME_FORMATTER));
        date.setClassName("post-date");
        H3 title = new H3(notification.getSubject());
        User author = notification.getAuthorUser();

        Paragraph content = new Paragraph(notification.getDescription());
        content.getStyle().set("maxWidth", "100%").set("overflow", "hidden").set("textOverflow", "ellipsis").set("whiteSpace", "nowrap");

        String[] nameAndPath = JsonUtil.getFileNameAndPath(author.getImagePath());
        if(nameAndPath != null) {
            image = new Image(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]), nameAndPath[0]);
            image.addClassName("notification-image");
        } else {
            image = new Image();
        }

        Button open = UIUtil.createTertiaryButton(VaadinIcon.CHEVRON_RIGHT, "İçerik", ButtonVariant.LUMO_SMALL);
        open.addClickListener(evt -> detailAction.accept(notification));

        add(
                UIUtil.horizontalFullJustifyContentBetween(new HorizontalLayout(image, new H4(author.getFullName())), date),
                title, content,
                UIUtil.horizontalFull(open));
    }




}
