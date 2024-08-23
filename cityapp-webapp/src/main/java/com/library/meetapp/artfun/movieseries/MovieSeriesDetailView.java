package com.library.meetapp.artfun.movieseries;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "movie-series-detail", layout = MainLayout.class)
public class MovieSeriesDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}
