package co.develhope.Upload.Download.dto;

import co.develhope.Upload.Download.entities.User;

public class DownloadProfilePictureDTO {

    private User user;
    private byte[] profileImage;

    public DownloadProfilePictureDTO() {}

    public DownloadProfilePictureDTO(User user, byte[] profileImage) {
        this.user = user;
        this.profileImage = profileImage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }
}
