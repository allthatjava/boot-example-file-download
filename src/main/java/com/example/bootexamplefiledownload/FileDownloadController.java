package com.example.bootexamplefiledownload;

import org.apache.catalina.connector.CoyoteOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class FileDownloadController {

    @GetMapping("/download_file")
    public void fileDownload(HttpServletResponse response) throws IOException {
        File file = new File("file\\wallpaper.jpg");

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+file.getName();

        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[8192];
        int read = -1;

        while( (read = inputStream.read()) != -1){
            outputStream.write(buffer, 0, read);
        }

        inputStream.close();
        outputStream.close();
    }

    @GetMapping("/download_generated_file")
    public void fileDownloadGenerated(HttpServletResponse response) throws IOException {
        String textFileContent = "Text File Contents";


        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=generated_file.txt";

        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(textFileContent.getBytes());
    }
}
