package com.library.meetapp.selfimp.timemanage;

import com.library.meetapp.dialog.AbstractDialog;
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
@Route(value = "time-management", layout = MainLayout.class)
public class TimeManagementView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> timeManagementPosts;

    @Override
    protected void initDialog() {
        dialogTitle = "Zaman Yönetimi İçeriği Ekle";
        category = "TIME";

    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        timeManagementPosts = postService.getPostsByCategory("TIME");
        grid.setItems(timeManagementPosts);
        grid.setVisible(!timeManagementPosts.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Zaman Yönetimi");
    }

    @Override
    protected List<Post> getItems() {
        return timeManagementPosts;
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