package co.develhope.Upload.Download.repositories;

import co.develhope.Upload.Download.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
