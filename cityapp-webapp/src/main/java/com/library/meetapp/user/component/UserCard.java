package com.library.meetapp.user.component;

import com.library.meetapp.component.UploadField;
import com.library.meetapp.entity.Post;
import com.library.meetapp.util.JsonUtil;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Span;
import com.library.meetapp.util.DateUtil;

public class UserCard extends HorizontalLayout {
    private Image image;
    private H4 fullName;
    private Span date;

    public UserCard(Post post) {

        String[] nameAndPath = JsonUtil.getFileNameAndPath(post.getUser().getImagePath());
        if(nameAndPath != null) {
            image = new Image(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]), nameAndPath[0]);
            image.addClassName("image-card");
        } else {
            image = new Image();
        }

        fullName = new H4(post.getUserFullName());

        date = new Span(DateUtil.format(post.getUpdatedAt() != null ? post.getUpdatedAt() : post.getCreatedAt(), DateUtil.USERFRIENDLY_DATETIME_FORMATTER));
        date.setClassName("user-date");

        add(image, new VerticalLayout(fullName, date));
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
