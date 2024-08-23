package com.library.meetapp.finance.investment;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "investment-detail", layout = MainLayout.class)
public class InvestmentDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}
