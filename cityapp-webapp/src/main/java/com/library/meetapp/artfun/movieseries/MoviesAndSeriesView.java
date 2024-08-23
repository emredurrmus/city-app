package com.library.meetapp.artfun.movieseries;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.navigation.ui.PostUtil;
import com.library.meetapp.post.component.PostCard;
import com.library.meetapp.service.PostService;
import com.library.meetapp.view.layout.MasterDetailView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@PermitAll
@Route(value = "movies-and-series", layout = MainLayout.class)
public class MoviesAndSeriesView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;
    private List<Post> moviesAndSeriesPosts;

    @Override
    protected void initDialog() {
        dialogTitle = "Film/Dizi İçeriği Ekle";
        category = "MOVIES";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        moviesAndSeriesPosts = postService.getPostsByCategory("MOVIES");
        grid.setItems(moviesAndSeriesPosts);
        grid.setVisible(!moviesAndSeriesPosts.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Filmler ve Diziler");
    }

    private void navigatePostDetail(Post post) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, post));
    }

    @Override
    protected List<Post> getItems() {
        return moviesAndSeriesPosts;
    }

    @Override
    public boolean matchesFilter(Post post, String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return true;
        }
        String lowerCaseFilter = filterText.toLowerCase();
        return post.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                post.getContent().toLowerCase().contains(lowerCaseFilter);
    }


}
