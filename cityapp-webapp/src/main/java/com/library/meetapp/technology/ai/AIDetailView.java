package com.library.meetapp.technology.ai;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "ai-detail" , layout = MainLayout.class)
public class AIDetailView extends PostDetailView<Post>{

    @Override
    protected void setPostDetails(Post post) {

    }
}
