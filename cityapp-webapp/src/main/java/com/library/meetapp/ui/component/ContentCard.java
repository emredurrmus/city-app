package com.library.meetapp.ui.component;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;


public class ContentCard extends VerticalLayout {

    private Text title = new Text("");
    private TextArea textArea = new TextArea();


    public ContentCard(String content) {
        addClassName("content-card");
        title.setClassName("title");

        VerticalLayout layout = new VerticalLayout();

        layout.add();
        layout.add(content);

        add(layout);

    }


    public void setTitle(String text) {
        title.setText(text);
    }

    public void setContent(String content) {
        textArea.setText(content);
    }









}
