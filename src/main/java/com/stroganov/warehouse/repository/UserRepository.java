package com.stroganov.warehouse.repository;

import com.stroganov.warehouse.domain.model.user.Authorities;
import com.stroganov.warehouse.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Transactional
    @Modifying
    @Query("""
            update User u set u.password = ?1, u.fullName = ?2, u.email = ?3, u.enabled = ?4, u.authorities = ?5
            where u.userName = ?6""")
    void update(String password, String fullName, String email, boolean enabled, Set<Authorities> authorities, String userName);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserName(String userName);

}
