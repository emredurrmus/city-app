package com.library.meetapp.technology.software;

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
import java.util.stream.Collectors;

@PermitAll
@Route(value = "software-development", layout = MainLayout.class)
public class SoftwareDevelopmentView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;
    private List<Post> softwareDevPosts;

    @Override
    protected void initDialog() {
        dialogTitle = "Yazılım Geliştirme İçeriği Ekle";
        category = "SD";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        softwareDevPosts = postService.getPostsByCategory("SD");
        grid.setItems(softwareDevPosts);
        grid.setVisible(!softwareDevPosts.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Yazılım Geliştirme");
    }

    private void navigatePostDetail(Post post) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, post));
    }

    @Override
    protected List<Post> getItems() {
        return softwareDevPosts;
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
}
