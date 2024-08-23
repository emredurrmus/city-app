package com.library.meetapp.post.component;

import com.library.meetapp.component.UploadField;
import com.library.meetapp.dialog.ConfirmDialog;
import com.library.meetapp.entity.Comment;
import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.Taste;
import com.library.meetapp.entity.User;
import com.library.meetapp.service.PostService;
import com.library.meetapp.util.AppCtx;
import com.library.meetapp.util.DateUtil;
import com.library.meetapp.util.JsonUtil;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableConsumer;

import java.util.*;

public class PostCard extends VerticalLayout {

    private Image image;

    private Avatar avatar;

    private User currentUser = AppCtx.getBean(User.class);
    private PostService postService = AppCtx.getBean(PostService.class);

    private Map<Post, List<String>> localCache = new HashMap<>();


    public static PostCard create(Post post, SerializableConsumer<Post> detailAction) {
        return new PostCard(post, detailAction);
    }

    public PostCard(Post post, SerializableConsumer<Post> detailAction) {
        setSpacing(false);
        setClassName("post-card");
        Span date = new Span(DateUtil.format(post.getCreatedAt(), DateUtil.USERFRIENDLY_DATETIME_FORMATTER));
        date.setClassName("post-date");
        H3 title = new H3(post.getTitle());
        User author = post.getUser();

        Paragraph content = new Paragraph(post.getContent());
        content.getStyle().set("maxWidth", "100%").set("overflow", "hidden").set("textOverflow", "ellipsis").set("whiteSpace", "nowrap");

        String[] nameAndPath = JsonUtil.getFileNameAndPath(author.getImagePath());
        if (nameAndPath != null) {
            image = new Image(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]), nameAndPath[0]);
            image.addClassName("image-overlay");
        } else {
            image = new Image();
        }

        Button open = UIUtil.createTertiaryButton(VaadinIcon.CHEVRON_RIGHT, "İçerik", ButtonVariant.LUMO_SMALL);
        open.addClickListener(evt -> detailAction.accept(post));

        setPostInfoCache(post);

        String likeCount = localCache.get(post).get(0);
        String commentCount = localCache.get(post).get(1);


        Button like = UIUtil.createPrimarySmallButton(VaadinIcon.THUMBS_UP);
        like.setSuffixComponent(new Span(likeCount));
        like.addClickListener(e -> {
            toggleLikeButton(like, post);
        });


        Button comment = UIUtil.createPrimarySmallButton(VaadinIcon.COMMENT_O);
        comment.setSuffixComponent(new Span(commentCount));


        HorizontalLayout header = new HorizontalLayout(image, new H4(author.getFullName()));
        header.setWidthFull();


        Button delete = UIUtil.createTertiaryButton(VaadinIcon.CLOSE, ButtonVariant.LUMO_ERROR);
        delete.setClassName("delete-button");
        delete.setVisible(currentUser.equals(author));
        delete.addThemeVariants(ButtonVariant.LUMO_SMALL);
        delete.addClickListener(evt -> showDeleteConfirmDialog(post));

        header.add(delete);


        add(
                UIUtil.horizontalFullJustifyContentBetween(header, date),
                title, content,
                UIUtil.horizontalFull(like,comment,open)
        );
    }

    private void toggleLikeButton(Button like, Post post) {
        Taste existTaste = postService.findTasteByPostAndUserIfExist(currentUser, post);
        if (existTaste != null || like.getClassNames().contains("active")) {
            // Like kaldırma işlemi
            postService.removeLike(currentUser,post);
            like.removeClassName("active");
            int currentLikes = Integer.parseInt(like.getSuffixComponent().getElement().getText());
            like.setSuffixComponent(new Span(String.valueOf(currentLikes - 1)));
        } else {
            // Like ekleme işlemi
            postService.saveLike(currentUser, post);
            like.addClassName("active");
            int currentLikes = Integer.parseInt(like.getSuffixComponent().getElement().getText());
            like.setSuffixComponent(new Span(String.valueOf(currentLikes + 1)));
        }
    }


    private void setPostInfoCache(Post post) {
        List<Comment> comments = new ArrayList<>();
        List<Taste> tastes = new ArrayList<>();

        if(postService.getCommentsByPost(post) != null) {
            comments = postService.getCommentsByPost(post);
        }
        if(postService.getTastesByPost(post) != null) {
            tastes = postService.getTastesByPost(post);
        }
        String likeCount =  String.valueOf(tastes.size());
        String commentCount = String.valueOf(comments.size());

        localCache.put(post ,Arrays.asList(likeCount, commentCount));
    }


    private void showDeleteConfirmDialog(Post post) {
        ConfirmDialog.show("Silme Onayı",
                "Bu gönderiyi silmek istediğinizden emin misiniz?",
                "Sil", ButtonVariant.LUMO_ERROR, () -> {
                    postService.deletePost(post);
                    UI.getCurrent().getPage().reload();
                });
    }
}
