package com.example.bootexamplefiledownload.controller;

import com.example.bootexamplefiledownload.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.CoyoteOutputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
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

    @GetMapping("/download_csv")
    public void fileDownloadCsv(HttpServletResponse response) throws IOException {

        List<Employee> list = Arrays.asList(new Employee(1L,"Brian", "brian@email.com", 47),
                                            new Employee( 2L, "Phil", "phil@email.com", 48));

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=generated_csv.csv";

        response.setHeader(headerKey, headerValue);

        writeEmployeeToCsv(response.getWriter(), list);
    }

    private void writeEmployeeToCsv(Writer writer, List<Employee> employees){
//        try(CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)){
        try( CSVPrinter csvPrinter = CSVFormat.DEFAULT.withHeader("ID", "Name", "Email", "Age").print(writer)){
            for (Employee e : employees) {
                csvPrinter.printRecord(e.getId(), e.getName(), e.getEmail(), e.getAge());
            }
        } catch (IOException e) {
            log.error("Error while writing csv", e);
        }

    }
}
