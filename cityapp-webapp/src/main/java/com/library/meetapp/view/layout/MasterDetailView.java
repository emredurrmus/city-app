package com.library.meetapp.view.layout;

import com.library.meetapp.abstractcore.AbstractEntity;
import com.library.meetapp.dialog.AbstractDialog;
import com.library.meetapp.dialog.AddPostDialog;
import com.library.meetapp.entity.Post;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public abstract class MasterDetailView<T extends AbstractEntity> extends VerticalLayout {

    protected HorizontalLayout header;
    protected VerticalLayout content = new VerticalLayout();
    protected Span title = new Span();
    protected Div detailNotificationDrawer = new Div();
    protected VerticalLayout layout = UIUtil.verticalFull();
    protected Button newPost = UIUtil.createTertiaryButton("Ekle", VaadinIcon.ABACUS);
    protected AddPostDialog dialog;
    protected String category;
    protected String dialogTitle;
    protected Grid<T> postGrid;
    protected TextField searchBar = new TextField();

    @PostConstruct
    private void initialize() {
        setClassName("main-content");
        layout.setSizeFull();
        detailNotificationDrawer.addClassName("notification-detail-drawer");
        postGrid = createGrid();
        postGrid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        postGrid.setWidthFull();
        postGrid.setAllRowsVisible(true);

        initGrid(postGrid);
        initDialog();

        dialog = new AddPostDialog(dialogTitle, category);

        title.setClassName("title");
        content.setSizeFull();
        content.add(postGrid);

        header = new HorizontalLayout();
        header.setClassName("");
        header.add(title);
        header.add(searchBar);
        header.add(newPost);
        header.setWidthFull();

        searchBar.setPlaceholder("Arama Yap...");
        searchBar.setValueChangeMode(ValueChangeMode.EAGER);
        searchBar.addValueChangeListener(e -> filterGrid(e.getValue()));

        newPost.addClickListener(evt -> dialog.open());

        setContent(content);

        init();

        layout.add(header);
        layout.add(content);

        SplitLayout contentWrapper = UIUtil.splitHorizontal(layout, detailNotificationDrawer);
        add(contentWrapper);
    }

    protected abstract void initDialog();

    public static MasterDetailView get() {
        return (MasterDetailView) UI.getCurrent().getChildren().filter(component -> component.getClass() == MasterDetailView.class)
                .findFirst().orElse(null);
    }

    public void addToNotificationDetailDrawer(com.vaadin.flow.component.Component component) {
        detailNotificationDrawer.removeAll();
        detailNotificationDrawer.add(component);
        detailNotificationDrawer.getElement().setAttribute("open", "true");
        layout.getStyle().set("--display-filters-wrapper", "none");
    }

    public void closeNotificationDetailDrawer() {
        detailNotificationDrawer.getElement().removeAttribute("open");
    }

    protected Grid<T> createGrid() {
        return new Grid<>();
    }

    protected abstract void initGrid(Grid<T> grid);

    public abstract void setContent(VerticalLayout content);

    protected void init() {
    }

    private void filterGrid(String filterText) {
        List<T> filteredItems = getItems().stream()
                .filter(item -> matchesFilter(item, filterText))
                .collect(Collectors.toList());
        postGrid.setItems(filteredItems);
    }

    protected abstract List<T> getItems();

    protected abstract boolean matchesFilter(T item, String filterText);



}
