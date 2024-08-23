package com.library.meetapp.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.upload.UploadI18N;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UIUtil {


    public static FormLayout createForm(int cols) {
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", cols, LabelsPosition.TOP));
        return form;
    }

    public static Button createPrimaryButton(String text,VaadinIcon icon, ButtonVariant... buttonVariants)  {
        Button button = new Button(text);
        button.setIcon(icon.create());
        button.addThemeVariants(buttonVariants);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    public static Button createPrimarySmallButton(VaadinIcon icon) {
        Button button = new Button();
        button.setIcon(icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        return button;
    }


    public static Button createPrimaryButton(String text,VaadinIcon icon)  {
        Button button = new Button(text);
        button.setIcon(icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }


    public static Button createPrimaryButton(VaadinIcon icon)  {
        Button button = new Button();
        button.setIcon(icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    public static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    public static Button createTertiaryButton(VaadinIcon icon, String text, ButtonVariant... variants) {
        Button button = new Button(text, icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.addThemeVariants(variants);
        return button;
    }

    public static Button createTertiaryButton(String text,VaadinIcon icon) {
        Button button = new Button(text);
        button.setIcon(icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY,ButtonVariant.LUMO_CONTRAST);
        return button;
    }

    public static Button createTertiaryButton(VaadinIcon icon, ButtonVariant... variants) {
        Button button = new Button(icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.addThemeVariants(variants);
        return button;
    }



    public static Button createTertiaryButton(String text) {
        Button button = new Button(text);
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY,ButtonVariant.LUMO_CONTRAST);
        return button;
    }

    public static VerticalLayout vertical(Component... components) {
        VerticalLayout layout = new VerticalLayout(components);
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }

    public static VerticalLayout verticalFull(Component... components) {
        VerticalLayout layout = new VerticalLayout(components);
        layout.setSizeFull();
        return layout;
    }

    public static SplitLayout splitHorizontal(Component left, Component right) {
        SplitLayout layout = new SplitLayout(left, right);
        layout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        layout.addThemeVariants(SplitLayoutVariant.LUMO_MINIMAL);
        layout.setSizeFull();
        return layout;
    }

    public static HorizontalLayout horizontal(Component... components) {
        HorizontalLayout layout = new HorizontalLayout(components);
        if (components.length > 1) {
            components[0].getElement().getStyle().set("margin-left", "0");
        }
        layout.setWidthFull();
        return layout;
    }


    public static HorizontalLayout horizontalFull(Component... components) {
        HorizontalLayout layout = new HorizontalLayout(components);
        layout.setSizeFull();
        return layout;
    }


    public static HorizontalLayout horizontalFullJustifyContentBetween(Component... components) {
        HorizontalLayout layout = horizontalFull(components);
        layout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        layout.setAlignItems(Alignment.BASELINE);
        return layout;
    }


    public static Button createTertiaryInlineButton(VaadinIcon icon, String text, ButtonVariant... variants) {
        Button button = new Button(text, icon.create());
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        button.addThemeVariants(variants);
        return button;
    }


    public static Div labelWithIcon(VaadinIcon icon, String text, String cssClass) {
        Div div = new Div(new Text(text));
        div.setClassName(cssClass);
        if (icon != null) {
            Icon iconComponent = icon.create();
            iconComponent.setClassName("icon-on-left");
            div.addComponentAsFirst(iconComponent);
        }
        return div;
    }


    public static UploadI18N uploadI18N() {
        UploadI18N i18n = new UploadI18N();
        i18n.setDropFiles(new UploadI18N.DropFiles().setOne("Dosyayı buraya sürükleyin...")
                        .setMany("Dosyaları buraya sürükleyin..."))
                .setAddFiles(new UploadI18N.AddFiles().setOne("Dosya ekle").setMany("Dosya ekle"))
                .setError(new UploadI18N.Error().setTooManyFiles("Dosya yükleme sınırı aşıldı.")
                        .setFileIsTooBig("Dosya boyutu sınırın üzerinde.")
                        .setIncorrectFileType("Dosya tipi uygun değil."))
                .setUploading(new UploadI18N.Uploading()
                        .setStatus(new UploadI18N.Uploading.Status().setConnecting("Başlanıyor...")
                                .setStalled("Yükleme durduruldu.").setProcessing("Yükleniyor..."))
                        .setRemainingTime(new UploadI18N.Uploading.RemainingTime().setPrefix("Kalan süre: ")
                                .setUnknown("Kalan süre bilinmiyor"))
                        .setError(new UploadI18N.Uploading.Error().setServerUnavailable("Server uygun değil")
                                .setUnexpectedServerError("Bilinmeyen hata oluştu")
                                .setForbidden("Yüklemeye izin verilmiyor")))
                .setUnits(Stream.of("Byte", "KB", "MB", "GB", "TB").collect(Collectors.toList()));
        return i18n;
    }



}
