package com.library.meetapp.main.view;

import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.entity.Post;
import com.library.meetapp.navigation.ui.PostUtil;
import com.library.meetapp.service.PostService;
import com.library.meetapp.ui.component.PopulerCard;
import com.library.meetapp.util.AppCtx;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PermitAll
public class MainView extends VerticalLayout {

    private PostService postService = AppCtx.getBean(PostService.class);

    private ListDataProvider<Post> dataProvider;

    private Grid<Post> grid;

    public  MainView() {
        add(new H1("Popüler Gönderiler"));
        setSizeFull();

        TextField searchField = new TextField();
        searchField.setPlaceholder("Ara...");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> applyFilter(e.getValue()));

        List<Post> popularPosts = postService.getTopLikedPosts();

        dataProvider = new ListDataProvider<>(popularPosts);

        grid = new Grid<>();
        grid.setDataProvider(dataProvider);
        grid.addComponentColumn(post -> PopulerCard.create(post, this::navigatePostDetail))
                .setHeader("Gönderiler");

        add(searchField, grid);
    }

    private void applyFilter(String filterText) {
        dataProvider.clearFilters();
        if (filterText != null && !filterText.isEmpty()) {
            dataProvider.addFilter(post -> {
                String lowerCaseFilter = filterText.toLowerCase();
                return post.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                        post.getContent().toLowerCase().contains(lowerCaseFilter);
            });
        }
    }

    private void navigatePostDetail(Post post) {
        getUI().ifPresent(ui -> PostUtil.navigateToPostDetail(ui, post));
    }
}
