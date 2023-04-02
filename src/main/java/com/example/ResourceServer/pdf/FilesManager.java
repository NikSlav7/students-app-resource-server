package com.example.ResourceServer.pdf;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Component
public class FilesManager {

    public static File multipartToFile(MultipartFile multipartFile) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(multipartFile.getInputStream());
        File output = new File(UUID.randomUUID().toString() + ".pdf");
        OutputStream outputStream = new FileOutputStream(output);
        byte[] buffer = new byte[2048];
        int len;
        while ((len = bufferedInputStream.read(buffer, 0, 2048)) != -1){
            outputStream.write(buffer);
        }
        outputStream.close();
        bufferedInputStream.close();
        return output;
    }
}
