package Repository;

import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.expired = :status WHERE u.email = :email")
    void updateExpiredByEmail(@Param("email")String email, @Param("status")boolean status);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password")String newPassword);
}
