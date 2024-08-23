package com.library.meetapp.sport.nutrition;

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
@Route(value = "nutrition", layout = MainLayout.class)
public class NutritionView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> nutritionPosts;

    @Override
    protected void initDialog() {
        dialogTitle = "Beslenme İçeriği Ekle";
        category = "NUT";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        nutritionPosts = postService.getPostsByCategory("NUT");
        grid.setItems(nutritionPosts);
        grid.setVisible(!nutritionPosts.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Beslenme");
    }

    @Override
    protected List<Post> getItems() {
        return nutritionPosts;
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
