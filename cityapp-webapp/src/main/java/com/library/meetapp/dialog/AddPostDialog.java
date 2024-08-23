package com.library.meetapp.dialog;

import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import com.library.meetapp.service.PostService;
import com.library.meetapp.util.AppCtx;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;

public class AddPostDialog extends AbstractDialog {

    PostService service = AppCtx.getBean(PostService.class);

    User currentUser = AppCtx.getBean(User.class);

    private Binder<Post> binder;
    private Post post = new Post();

    public AddPostDialog(String header, String category) {
        setTitle(header);

        binder = new Binder<>(Post.class);
        binder.forField(title).asRequired("Başlık gerekli").bind(Post::getTitle, Post::setTitle);
        binder.forField(comment).asRequired("İçerik Gerekli").bind( Post::getContent, Post::setContent);

        binder.setBean(post);

        // Kaydetme butonu ve işlemi
        Button saveButton = new Button("Kaydet", event ->  {
            savePost(category);
        });
        addToToolbar(saveButton);


    }


    private void savePost(String category) {
        if (binder.isValid()) {
            Post post = binder.getBean();
            post.setCategory(category);
            try {
                service.save(currentUser, post);
                close();
                UI.getCurrent().getPage().reload();
            } catch (Exception e) {
                e.printStackTrace(); // Hata mesajını yazdırma
                Notification.show("Bir hata oluştu: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            }
        } else {
            Notification.show("Lütfen tüm alanları doldurun", 3000, Notification.Position.TOP_CENTER);
        }
    }



}
