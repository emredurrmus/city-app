package com.library.meetapp.technology.ai;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.navigation.ui.PostUtil;
import com.library.meetapp.post.component.PostCard;
import com.library.meetapp.service.PostService;
import com.library.meetapp.view.layout.MasterDetailView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PermitAll
@Route(value = "ai", layout = MainLayout.class)
public class AIView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> aiPost;

    @Override
    protected void initDialog() {
        dialogTitle = "Yapay Zeka İçeriği Ekle";
        category = "AI";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        aiPost = postService.getPostsByCategory("AI");
        grid.setItems(aiPost);
        grid.setVisible(!aiPost.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Yapay Zeka");
    }

    @Override
    protected List<Post> getItems() {
        return aiPost;
    }

    @Override
    protected boolean matchesFilter(Post post, String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        String lowerCaseFilter = filterText.toLowerCase();
        return post.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                post.getContent().toLowerCase().contains(lowerCaseFilter);
    }

    private void navigatePostDetail(Post post) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, post));
    }
}
