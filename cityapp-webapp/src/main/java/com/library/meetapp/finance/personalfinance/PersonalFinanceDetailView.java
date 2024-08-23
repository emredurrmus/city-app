package com.library.meetapp.finance.personalfinance;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "personal-finance-detail", layout = MainLayout.class)
public class PersonalFinanceDetailView extends PostDetailView<Post> {


    @Override
    protected void setPostDetails(Post post) {

    }
}
