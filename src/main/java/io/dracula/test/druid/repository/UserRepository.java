package io.dracula.test.druid.repository;

import io.dracula.test.druid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dk
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
