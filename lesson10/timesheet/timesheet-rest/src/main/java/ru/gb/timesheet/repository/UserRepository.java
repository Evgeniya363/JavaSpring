package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.timesheet.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByLogin(String username);

    @Query(nativeQuery = true, value =
            "select r.name from users u " +
            "left join users_roles l on u.id = l.user_id " +
            "left join role r on l.role_id = r.id " +
            "where u.id = :userId")
    List<String> findUserRolesByUserId(Long userId);

}
