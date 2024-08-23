package com.library.meetapp.technology.netsecurity;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;


@Route(value = "internet-detail" , layout = MainLayout.class)
public class InternetDetailView  extends PostDetailView<Post>{


    @Override
    protected void setPostDetails(Post post) {

    }
}
