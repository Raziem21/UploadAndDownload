package co.develhope.Upload.Download.controllers;

import co.develhope.Upload.Download.services.FileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

/*
Singolo upload
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        return fileStorageService.upload(file);
    }
*/

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws Exception {
        List<String> fileNames = new ArrayList<>();
        for(MultipartFile file: files) {
           String singleUploadedFileName = fileStorageService.upload(file);
           fileNames.add(singleUploadedFileName);
        }
        return fileNames;
    }


    @GetMapping("/download")
    public @ResponseBody byte[] fileDownload(@RequestParam String fileName, HttpServletResponse response) throws Exception{
        System.out.println("Downloading " + fileName);
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "gif" -> response.setContentType(MediaType.IMAGE_GIF_VALUE);
            case "jpg", "jpeg" -> response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            case "png" -> response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        return fileStorageService.download(fileName);
    }
}
