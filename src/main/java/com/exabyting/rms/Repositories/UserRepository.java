package com.exabyting.rms.Repositories;

import com.exabyting.rms.Entities.Helper.Role;
import com.exabyting.rms.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    Page<User> findByRolesContaining(Role role, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE :role IS NULL OR :role MEMBER OF u.roles")
    Page<User>findAllWithRoleFilter(
            @Param("role") Role role, Pageable pageable
    );

    User findByEmail(String email);


}
