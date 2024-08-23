package com.library.meetapp.component;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.library.meetapp.data.service.FileSystemService;
import com.library.meetapp.util.AppCtx;
import com.library.meetapp.util.JsonUtil;
import com.library.meetapp.util.StringUtils;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.AnchorTarget;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;

import elemental.json.Json;
import elemental.json.JsonObject;

public class UploadField extends CustomField<String> {

    private Upload reportUpload;
    private Anchor reportLink;

    private String folder;
    private String value;
    private boolean preview = false;

    private MemoryBuffer buffer = new MemoryBuffer();

    public UploadField() {
        reportUpload = new Upload();
        reportUpload.setI18n(UIUtil.uploadI18N());
        reportUpload.setDropAllowed(false);
        reportUpload.setReceiver(buffer);
        reportUpload.addSucceededListener(evt -> processUploadedFile(evt.getFileName(), evt.getMIMEType(), evt.getContentLength()));
        reportUpload.addFailedListener(evt -> Notification.show(String.format("Dosya yüklenemedi: %s", evt.getFileName())));
        reportUpload.addFileRejectedListener(evt -> Notification.show(String.format("Dosya yüklenemedi: %s", evt.getErrorMessage())));

        reportLink = new Anchor();
        add(UIUtil.horizontal(reportUpload, reportLink));
    }

    @Override
    protected String generateModelValue() {
        return value;
    }

    @Override
    protected void setPresentationValue(String newPresentationValue) {
        String[] nameAndPath = JsonUtil.getFileNameAndPath(newPresentationValue);
        reportLink.setVisible(nameAndPath != null);
        reportLink.removeAll();
        if (nameAndPath != null) {
            reportLink.setHref(getFileStreamResource(nameAndPath[0], nameAndPath[1]));
            reportLink.setTarget(AnchorTarget.BLANK);
            if (preview) {
                Image image = new Image(getFileStreamResource(nameAndPath[0], nameAndPath[1]), (nameAndPath[0]));
                image.setWidthFull();
                reportLink.add(image);
            } else {
                reportLink.add(UIUtil.createTertiaryButton(VaadinIcon.EXTERNAL_LINK, nameAndPath[0]));
            }
        }
    }

    private void processUploadedFile(String filename, String mimetype, long len) {
        try {
            FileSystemService fileService = AppCtx.getBean(FileSystemService.class);
            filename = StringUtils.replaceTrChars(filename);
            String path = fileService.saveFile(folder, filename, buffer.getInputStream());
            JsonObject jsonValue = Json.createObject();
            jsonValue.put("name", filename);
            jsonValue.put("path", path);
            value = jsonValue.toJson();
            setValue(value);
            reportUpload.getElement().setPropertyJson("files", Json.createArray());
        } catch (Exception e) {
            Notification.show(filename + " isimli dosya kaydedilemedi:" + e.getMessage());
        }
    }

    public static StreamResource getFileStreamResource(String filename, String location) {
        return new StreamResource(filename, new InputStreamFactory() {
            @Override
            public InputStream createInputStream() {
                try {
                    return new FileInputStream(location);
                } catch (FileNotFoundException e) {
                    Notification.show(filename + " isimli dosyaya erişilemedi");
                    return new ByteArrayInputStream(new byte[0]);
                }
            }
        });
    }

    public void setPreviewImage(boolean b) {
        preview = b;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        reportUpload.setVisible(!readOnly);
    }


}

