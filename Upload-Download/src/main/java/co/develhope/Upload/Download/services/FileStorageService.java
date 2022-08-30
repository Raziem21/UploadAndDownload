package co.develhope.Upload.Download.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    /*
        @Value("&{fileRepositoryFolder}")
        private String fileRepositoryFolder;
    */
    private final String fileRepositoryFolder = "C:\\fileRepositoryFolder";

    /**
     * @param file File from upload controller
     * @return New file with extension
     * @throws IOException if filter is not writable
     */

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;
        File finalFolder = new File(fileRepositoryFolder);
        if (!finalFolder.exists()) throw new IOException("Folder does not exist");
        if (!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");

        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);
        //if(completeFileName.exists()) throw new IOException("File conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if (!fileFromRepository.exists()) throw new IOException("File does not exist");
        return IOUtils.toByteArray(new FileReader(fileRepositoryFolder));
    }

    public void remove(String fileName) throws Exception {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if (!fileFromRepository.exists()) return;
        boolean deleteResult = fileFromRepository.delete();
        if(deleteResult == false) throw new Exception("Cannot delete file");
    }
}