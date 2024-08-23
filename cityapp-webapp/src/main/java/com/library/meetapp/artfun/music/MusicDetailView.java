package com.library.meetapp.artfun.music;

import com.library.meetapp.entity.Post;
import com.library.meetapp.layout.MainLayout;
import com.library.meetapp.view.layout.PostDetailView;
import com.vaadin.flow.router.Route;

@Route(value = "music-detail", layout = MainLayout.class)
public class MusicDetailView extends PostDetailView<Post> {

    @Override
    protected void setPostDetails(Post post) {

    }
}
