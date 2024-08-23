package com.library.meetapp.sport.exercise;

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
@Route(value = "exercise", layout = MainLayout.class)
public class ExerciseView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> exercisePosts;

    @Override
    protected void initDialog() {
        dialogTitle = "Egzersiz İçeriği Ekle";
        category = "EXE";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        exercisePosts = postService.getPostsByCategory("EXE");
        grid.setItems(exercisePosts);
        grid.setVisible(!exercisePosts.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Egzersiz");
    }

    @Override
    protected List<Post> getItems() {
        return exercisePosts;
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
