package com.library.meetapp.selfimp.careerdev;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "career-detail", layout = MainLayout.class)
public class CareerDevDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}

