package com.library.meetapp.technology.mobile;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "mobileapp-detail" , layout = MainLayout.class)
public class MobileAppsDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}
