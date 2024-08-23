package com.library.meetapp.ui.component;

import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import java.util.ArrayList;
import java.util.List;

public class SideMenuItem extends Div {

    private int level = 0;

    private Component link;
    private Class<? extends Component> navigationTarget;
    private String text;

    protected Button expandCollapse;

    private List<SideMenuItem> subItems;
    private boolean subItemsVisible;

    public SideMenuItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        link.getElement().insertChild(0, new Icon(icon).getElement());
    }

    public SideMenuItem(Image image, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        link.getElement().insertChild(0, image.getElement());
    }

    public SideMenuItem(String text, Class<? extends Component> navigationTarget) {
        setClassName("menu-item");
        setLevel(0);

        this.text = text;
        this.navigationTarget = navigationTarget;

        if (navigationTarget != null) {
            RouterLink routerLink = new RouterLink(navigationTarget);
            routerLink.add(new Span(text));
            routerLink.setClassName("menu-item-link");
            routerLink.setHighlightCondition(HighlightConditions.sameLocation());
            this.link = routerLink;

        } else {
            Div div = new Div(new Span(text));
            div.addClickListener(e -> expandCollapse.click());
            div.setClassName("menu-item-link");
            this.link = div;
        }

        expandCollapse = UIUtil.createTertiaryButton(VaadinIcon.CARET_UP, ButtonVariant.LUMO_SMALL);
        expandCollapse.addClickListener(event -> setSubItemsVisible(!subItemsVisible));
        expandCollapse.setVisible(false);

        subItems = new ArrayList<>();
        subItemsVisible = true;

        add(link, expandCollapse);
    }

    public SideMenuItem[] getSubItems() {
        setSubItemsVisible(false);
        return subItems.toArray(new SideMenuItem[0]);
    }

    public boolean isHighlighted(AfterNavigationEvent e) {
        return link instanceof RouterLink && ((RouterLink) link).getHighlightCondition().shouldHighlight((RouterLink) link, e);
    }

    public void setLevel(int level) {
        this.level = level;
        if (level > 0) {
            getElement().setAttribute("level", Integer.toString(level));
        }
    }

    public int getLevel() {
        return level;
    }

    public Class<? extends Component> getNavigationTarget() {
        return navigationTarget;
    }

    public void addSubItem(SideMenuItem item) {
        if (!expandCollapse.isVisible()) {
            expandCollapse.setVisible(true);
        }
        item.setLevel(getLevel() + 1);
        subItems.add(item);
    }

    private void setSubItemsVisible(boolean visible) {
        if (level == 0) {
            expandCollapse.setIcon(new Icon(visible ? VaadinIcon.CARET_UP : VaadinIcon.CARET_DOWN));
        }
        subItems.forEach(item -> item.setVisible(visible));
        subItemsVisible = visible;
    }

    public String getText() {
        return text;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        // If true, we only update the icon. If false, we hide all the sub items
        if (visible) {
            if (level == 0) {
                expandCollapse.setIcon(new Icon(VaadinIcon.CARET_DOWN));
            }
        } else {
            setSubItemsVisible(visible);
        }
    }
}
