package com.library.meetapp.finance.personalfinance;

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
@Route(value = "personal-finance", layout = MainLayout.class)
public class PersonalFinanceView extends MasterDetailView<Post> {

    @Autowired
    private PostService postService;

    private List<Post> personalFinancePost;


    @Override
    protected void initDialog() {
        dialogTitle = "Kişisel Finans İçeriği Ekle";
        category = "PF";
    }

    @Override
    protected void initGrid(Grid<Post> grid) {
        personalFinancePost = postService.getPostsByCategory("PF");
        grid.setItems(personalFinancePost);
        grid.setVisible(!personalFinancePost.isEmpty());
        grid.addComponentColumn(post -> PostCard.create(post, this::navigatePostDetail));
    }

    @Override
    public void setContent(VerticalLayout content) {
        title.setText("Kişisel Finans");
    }

    @Override
    protected List<Post> getItems() {
        return personalFinancePost;
    }

    private void navigatePostDetail(Post post) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, post));
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