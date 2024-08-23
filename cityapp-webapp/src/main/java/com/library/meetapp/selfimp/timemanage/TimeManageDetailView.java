package com.library.meetapp.selfimp.timemanage;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "time-manage-detail", layout = MainLayout.class)
public class TimeManageDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}

