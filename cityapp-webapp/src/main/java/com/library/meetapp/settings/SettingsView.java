package com.library.meetapp.settings;

import com.library.meetapp.component.UploadField;
import com.library.meetapp.dialog.ConfirmDialog;
import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.service.PostService;
import com.library.meetapp.service.UserService;
import com.library.meetapp.util.AppCtx;
import com.library.meetapp.util.DateUtil;
import com.library.meetapp.util.JsonUtil;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "settings", layout = MainLayout.class)
@PermitAll
public class SettingsView extends VerticalLayout {

    private UserService userService = AppCtx.getBean(UserService.class);
    private PostService postService = AppCtx.getBean(PostService.class);

    private User currentUser = AppCtx.getBean(User.class);

    private UploadField uploadField;
    private VerticalLayout imageWrapper = new VerticalLayout();
    private Binder<User> binder;
    private Image image;
    private Button save, edit;
    private TextField firstName, lastName, userName;
    private PasswordField password;
    private boolean hasEdit = false;
    private Grid<Post> postGrid;

    public SettingsView() {
        uploadField = new UploadField();
        uploadField.setFolder("userpp");
        uploadField.setVisible(false);

        userName = new TextField("Kullanıcı Adı");
        firstName = new TextField("Ad");
        lastName = new TextField("Soyad");
        password = new PasswordField("Şifre");

        save = UIUtil.createPrimaryButton("Kaydet");
        edit = UIUtil.createPrimaryButton("Düzenle", VaadinIcon.EDIT, ButtonVariant.LUMO_SMALL);

        initBinder();

        binder.setBean(currentUser);

        edit.addClickListener(evt -> setUploadFieldVisible(hasEdit));

        save.addClickListener(evt -> {
            userService.save(binder.getBean(), password.getValue());
            setUploadFieldVisible(hasEdit);
            UI.getCurrent().getPage().reload();
        });
        save.addClassName("button-right");

        uploadField.addValueChangeListener(evt -> {
            imageWrapper.remove(image);
            uploadField.setPreviewImage(true);
        });

        String[] nameAndPath = JsonUtil.getFileNameAndPath(currentUser.getImagePath());
        if (nameAndPath != null) {
            image = new Image(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]), nameAndPath[0]);
            image.addClassName("image");
            imageWrapper.add(image, uploadField, edit);
        } else {
            uploadField.setVisible(true);
            imageWrapper.add(uploadField);
        }

        FormLayout layout = UIUtil.createForm(2);
        layout.add(userName, 2);
        layout.add(firstName, lastName);
        layout.add(password);

        add(new HorizontalLayout(imageWrapper, layout), save);

        // Kullanıcının gönderilerini gösterecek grid'i oluşturun
        createPostGrid();

        // Kullanıcının gönderilerini yükleyin
        loadUserPosts();
    }

    private void setUploadFieldVisible(boolean hasEdit) {
        this.hasEdit = !hasEdit;
        uploadField.setVisible(this.hasEdit);
    }

    private void initBinder() {
        binder = new Binder<>();
        binder.forField(uploadField).bind(User::getImagePath, User::setImagePath);
        binder.forField(userName).bind(User::getUserName, User::setUserName);
        binder.forField(firstName).bind(User::getFirstName, User::setFirstName);
        binder.forField(lastName).bind(User::getLastName, User::setLastName);
    }

    private void createPostGrid() {
        postGrid = new Grid<>(Post.class);
        postGrid.setColumns("title", "content");
        postGrid.addColumn(post -> DateUtil.format(post.getCreatedAt(), DateUtil.USERFRIENDLY_DATETIME_FORMATTER))
                .setHeader("Tarih");
        postGrid.addComponentColumn(this::createDeleteButton).setHeader("Actions");

        add(postGrid);
    }

    private Button createDeleteButton(Post post) {
        Button deleteButton = new Button("Sil", VaadinIcon.TRASH.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(event -> {
            ConfirmDialog.show(
                    "Silme Onayı",
                    "Bu gönderiyi silmek istediğinizden emin misiniz?",
                    "Sil", ButtonVariant.LUMO_ERROR, () -> {
                        postService.deletePost(post);
                        loadUserPosts();
                    }
            );
        });
        return deleteButton;
    }

    private void loadUserPosts() {
        postGrid.setItems(postService.getPostsByUser(currentUser));
    }
}
