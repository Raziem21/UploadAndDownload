package co.develhope.Upload.Download.services;

import co.develhope.Upload.Download.dto.DownloadProfilePictureDTO;
import co.develhope.Upload.Download.entities.User;
import co.develhope.Upload.Download.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    private User getUser(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) throw new Exception("User is not present");
        return optionalUser.get();
    }

    public User uploadProfilePicture(Long userId, MultipartFile profilePicture) throws Exception {
        User user = getUser(userId);
        if(user.getProfilePicture() != null) {
            fileStorageService.remove(user.getProfilePicture());
        }
        String fileName = fileStorageService.upload(profilePicture);
        user.setProfilePicture(fileName);
        return userRepository.save(user);
    }

    public DownloadProfilePictureDTO downloadProfilePicture(Long id) throws Exception {
        User user = getUser(id);
        DownloadProfilePictureDTO dto = new DownloadProfilePictureDTO();
        dto.setUser(user);

        if (user.getProfilePicture() == null) return dto;

        byte[] profilePictureBytes = fileStorageService.download(user.getProfilePicture());
        dto.setProfileImage(profilePictureBytes);
        return dto;
    }

    public void remove(Long id) throws Exception {
        User user = getUser(id);
        if(user.getProfilePicture() != null) {
            fileStorageService.remove(user.getProfilePicture());
        }
        userRepository.deleteById(id);
    }
}
