package com.library.meetapp.finance.entrepreuner;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route(value = "entrepreneur-detail", layout = MainLayout.class)
public class EntrepreneurshipDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }


}
