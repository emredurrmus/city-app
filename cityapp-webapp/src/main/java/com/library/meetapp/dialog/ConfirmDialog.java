package com.library.meetapp.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.server.Command;

public class ConfirmDialog extends AbstractDialog {

    private Button confirm;

    public ConfirmDialog(String title, String message, String confirmText, ButtonVariant confirmButtonVariant, Command confirmAction) {
        super(title);
        setWidth("350px");
        setHeight("230px");
        setSize("small");

        setContent(new Span(message));

        this.confirm = new Button(confirmText, evt -> {
            confirmAction.execute();
            close();
        });
        confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY, confirmButtonVariant);
        confirm.addClassName("confirm-button");
        confirm.setDisableOnClick(true);

        addToToolbar(confirm);
    }
    public ConfirmDialog(String title, String message, String confirmText, ButtonVariant confirmButtonVariant, Command confirmAction,
                         String rejectText, ButtonVariant rejectButtonVariant, Command rejectAction) {
        this(title, message, confirmText, confirmButtonVariant, confirmAction);
        Button reject = new Button(rejectText, evt -> {
            close();
            rejectAction.execute();
        });
        reject.setDisableOnClick(true);
        reject.addThemeVariants(ButtonVariant.LUMO_PRIMARY, rejectButtonVariant);
        addToToolbar(reject);
    }

    public void addCancelAction(Command cancelAction) {
        super.addCancelAction(cancelAction);
    }

    public static void show(String title, String message, String confirmText, ButtonVariant confirmButtonVariant, Command confirmAction) {
        ConfirmDialog dialog = new ConfirmDialog(title, message, confirmText, confirmButtonVariant, confirmAction);
        dialog.open();
    }

    public static void show(String title, String message, Command confirmAction) {
        ConfirmDialog dialog = new ConfirmDialog(title, message, title, ButtonVariant.LUMO_PRIMARY, confirmAction);
        dialog.open();
    }

}
