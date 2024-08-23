package com.library.meetapp.technology.mobile;

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
@Route(value = "mobile-apps", layout = MainLayout.class)
public class MobileAppsView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> mobileAppsPost;

    @Override
    protected void initDialog() {
        dialogTitle = "Mobil Uygulamalar İçeriği Ekle";
        category = "MA";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        mobileAppsPost = postService.getPostsByCategory("MA");
        grid.setItems(mobileAppsPost);
        grid.setVisible(!mobileAppsPost.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Mobil Uygulamalar");
    }

    @Override
    protected List<Post> getItems() {
        return mobileAppsPost;
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
