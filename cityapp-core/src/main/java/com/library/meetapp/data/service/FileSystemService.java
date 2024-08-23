package com.library.meetapp.data.service;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileSystemService {


    public String saveFile(String folderName, String filename, InputStream content) throws IOException, IOException {
        File folder = new File("\\upload" + folderName + File.separator);
        folder.mkdirs();
        File fileToSave = new File(folder.getAbsolutePath() + File.separator + filename);
        FileOutputStream fos = new FileOutputStream(fileToSave);
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = content.read(data, 0, data.length)) != -1) {
            fos.write(data, 0, nRead);
        }

        fos.flush();
        fos.close();

        return fileToSave.getAbsolutePath();
    }

}
