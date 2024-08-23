package com.library.meetapp.dialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.Command;

public abstract class AbstractDialog extends Dialog {

    protected TextField title;
    protected TextArea comment;
    int charLimit = 4000;

    private VerticalLayout content;

    protected Button cancel;

    public AbstractDialog(String title) {
        this();
        setTitle(title);
    }

    public AbstractDialog() {
        setWidth("650px");
        setHeight("650px");

        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        title = new TextField("Başlık");
        title.setWidthFull();

        comment = new TextArea("İçerik");
        comment.setSizeFull();
        comment.setHeight("300px");
        comment.setMaxLength(charLimit);
        comment.setValueChangeMode(ValueChangeMode.EAGER);
        comment.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });


        content = new VerticalLayout();
        add(content);

        addToContent(title,comment);

        this.cancel = new Button("Vazgeç", evt -> close());
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        addToToolbar(cancel);
    }

    protected void setTitle(String titleText) {
        setHeaderTitle(titleText);
    }

    protected void setHeader(Component... components) {
        getHeader().removeAll();
        getHeader().add(components);
    }

    protected void setContent(Component... components) {
        content.removeAll();
        addToContent(components);
    }

    protected void addToContent(Component... components) {
        content.add(components);
    }

    protected void addToToolbar(Component... components) {
        getFooter().add(components);
    }

    protected void _insertToToolbar(Component component) {
        getFooter().add(component);
    }

    protected void setSize(String sizeTheme) {
        getElement().getThemeList().add(sizeTheme);
    }

    protected void asWizard() {
        addClassName("wizard");
    }

    protected void addCancelAction(Command cancelAction) {
        cancel.addClickListener(evt -> cancelAction.execute());
    }
}
