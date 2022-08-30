package co.develhope.Upload.Download.controllers;

import co.develhope.Upload.Download.dto.DownloadProfilePictureDTO;
import co.develhope.Upload.Download.entities.User;
import co.develhope.Upload.Download.repositories.UserRepository;
import co.develhope.Upload.Download.services.FileStorageService;
import co.develhope.Upload.Download.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @PostMapping("/{id}/profile")
    public User uploadProfileImage(@PathVariable Long id, @RequestParam MultipartFile profilePicture) throws Exception {
        return userService.uploadProfilePicture(id, profilePicture);
    }

    @GetMapping("/{id}/getProfile")
    public @ResponseBody byte[] getProfileImage(@PathVariable Long id, HttpServletResponse response) throws Exception {
        DownloadProfilePictureDTO downloadProfilePictureDTO = userService.downloadProfilePicture(id);
        String fileName = downloadProfilePictureDTO.getUser().getProfilePicture();
        String extension = FilenameUtils.getExtension(downloadProfilePictureDTO.getUser().getProfilePicture());
        switch (extension) {
            case "gif" -> response.setContentType(MediaType.IMAGE_GIF_VALUE);
            case "jpg", "jpeg" -> response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            case "png" -> response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        return downloadProfilePictureDTO.getProfileImage();
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/getOne")
    public Optional<User> getOne(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PutMapping
    public void update(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        userRepository.save(user);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) throws Exception {
        userService.remove(id);
    }
}
