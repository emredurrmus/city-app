package com.library.meetapp.ui.component;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouteParameters;

public class MenuItem extends SideNavItem  {

    public MenuItem(String label) {
        super(label);
    }

    public MenuItem(String label, String path) {
        super(label, path);

    }

    public MenuItem(String label, Class<? extends Component> view) {
        super(label, view);
    }

    public MenuItem(String label, Class<? extends Component> view, RouteParameters routeParameters) {
        super(label, view, routeParameters);
    }

    public MenuItem(String label, Class<? extends Component> view, Component prefixComponent) {
        super(label, view, prefixComponent);
    }
}
