package com.library.meetapp.view.layout;

import com.library.meetapp.component.UploadField;
import com.library.meetapp.entity.Comment;
import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import com.library.meetapp.service.PostService;
import com.library.meetapp.user.component.UserCard;
import com.library.meetapp.util.JsonUtil;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@PermitAll
public abstract class PostDetailView<ITEM> extends VerticalLayout implements HasUrlParameter<Long> {

    @Autowired
    protected PostService postService;

    @Autowired
    protected User currentUser;

    private Long postId;

    protected H3 title = new H3();
    protected Paragraph content = new Paragraph();

    protected TextField editTitle = new TextField("Başlık");
    protected TextArea editContent = new TextArea("İçerik");


    protected MessageList messageList = new MessageList();
    protected MessageInput input = new MessageInput();

    private Button editButton;
    private Button saveButton;

    @PostConstruct
    public void initialize() {
        if (postId != null) {
            initPost(postId);
        }
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        this.postId = id;
        if (title.getText().isEmpty() && content.getText().isEmpty()) {
            initPost(id);
        }
    }

    private void initPost(Long id) {

        Post post = postService.findPostById(id).orElseThrow();
        title.setText(post.getTitle());
        content.setText(post.getContent());

        UserCard userCard = new UserCard(post);

        // Posta ait yorumları al
        List<Comment> comments = postService.getCommentsByPost(post);

        List<MessageListItem> items = new ArrayList<>();
        // Yorumları MessageList'e ekle
        for (Comment comment : comments) {
            MessageListItem messageListItem = createMessageListItem(comment);
            items.add(messageListItem);
        }
        messageList.setItems(items);


        input.addSubmitListener(submitEvent -> {
            String commentText = submitEvent.getValue(); // Kullanıcının girdiği yorum metni

            // Yeni yorumu veritabanına kaydet
            Comment comment = new Comment();
            comment.setComment(commentText);
            comment.setCreatedAt(LocalDateTime.now());
            postService.saveComment(currentUser, post, comment);

            String[] nameAndPath = JsonUtil.getFileNameAndPath(comment.getAuthorComment().getImagePath());

            MessageListItem newMessage = new MessageListItem(
                    commentText, Instant.now(), comment.getAuthorComment().getFullName());
            newMessage.setUserImageResource(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]));

            // Yeni yorumu MessageList'e ekle
            List<MessageListItem> updatedItems = new ArrayList<>(messageList.getItems());
            updatedItems.add(newMessage);
            messageList.setItems(updatedItems);
        });

        editButton = UIUtil.createPrimaryButton("",VaadinIcon.EDIT, ButtonVariant.LUMO_SMALL);
        editButton.setVisible(currentUser.equals(post.getUser()));

        saveButton = UIUtil.createPrimaryButton("Kaydet", VaadinIcon.CHECK, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
        saveButton.setVisible(editButton.isVisible());

        editButton.addClickListener(e -> enableEditing(post));

        VerticalLayout layout = UIUtil.verticalFull();
        layout.add(UIUtil.horizontal(editButton));
        layout.add(userCard);
        layout.add(title, content);
        layout.addClassName("detail-content");

        VerticalLayout messageWrapper = UIUtil.verticalFull();
        messageWrapper.add(messageList, input);
        messageWrapper.addClassName("message-content");

        add(layout, messageWrapper);
    }

    private void enableEditing(Post post) {
        int charLimit = 4000;
        removeAll();

        editTitle.setValue(post.getTitle());
        editContent.setValue(post.getContent());

        editTitle.setWidthFull();

        editContent.setSizeFull();
        editContent.setHeight("300px");
        editContent.setMaxLength(charLimit);
        editContent.setValueChangeMode(ValueChangeMode.EAGER);
        editContent.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });


        Button cancel = UIUtil.createPrimaryButton("Vazgeç", VaadinIcon.CLOSE,ButtonVariant.LUMO_SMALL);;
        VerticalLayout layout = UIUtil.verticalFull();
        layout.addClassName("detail-content");
        layout.add(editTitle, editContent);
        layout.add(new HorizontalLayout(cancel ,saveButton));

        cancel.addClickListener(e -> turnMainPage(post));
        saveButton.addClickListener(s -> {
            post.setTitle(editTitle.getValue());
            post.setContent(editContent.getValue());
            try {
                postService.saveEditPost(currentUser, post);
                UI.getCurrent().getPage().reload();
            } catch (Exception e) {
                e.printStackTrace(); // Hata mesajını yazdırma
                Notification.show("Bir hata oluştu: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            }
        });

        add(layout);
    }

    private void turnMainPage(Post post) {
        removeAll();

        UserCard userCard = new UserCard(post);

        VerticalLayout messageWrapper = UIUtil.verticalFull();
        messageWrapper.add(messageList, input);
        messageWrapper.addClassName("message-content");


        VerticalLayout layout = UIUtil.verticalFull();
        layout.add(UIUtil.horizontal(editButton));
        layout.add(userCard);
        layout.add(title, content);
        layout.addClassName("detail-content");

        add(layout, messageWrapper);
    }


    private MessageListItem createMessageListItem(Comment comment) {
        String[] nameAndPath = JsonUtil.getFileNameAndPath(comment.getAuthorComment().getImagePath());
        MessageListItem newMessage = new MessageListItem(
                comment.getComment(),
                comment.getCreatedAt().toInstant(ZoneOffset.MAX),
                comment.getAuthorComment().getFullName());
        newMessage.setUserImageResource(UploadField.getFileStreamResource(nameAndPath[0], nameAndPath[1]));

        return newMessage;
    }

    protected abstract void setPostDetails(Post post);
}
