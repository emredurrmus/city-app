package com.library.meetapp.society.economy;

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
@Route(value = "economy", layout = MainLayout.class)
public class EconomyView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> economyPosts;

    @Override
    protected void initDialog() {
        dialogTitle = "Ekonomi İçeriği Ekle";
        category = "ECONOMY";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        List<Post> economyPosts = postService.getPostsByCategory("ECONOMY");
        grid.setItems(economyPosts);
        grid.setVisible(!economyPosts.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Ekonomi");
    }

    @Override
    protected List<Post> getItems() {
        return economyPosts;
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
