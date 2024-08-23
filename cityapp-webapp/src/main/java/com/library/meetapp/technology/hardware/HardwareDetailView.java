package com.library.meetapp.technology.hardware;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "hardware-detail" , layout = MainLayout.class)
public class HardwareDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}
