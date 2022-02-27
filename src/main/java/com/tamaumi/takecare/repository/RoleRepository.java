package com.tamaumi.takecare.repository;

import com.tamaumi.takecare.common.enums.ERole;
import com.tamaumi.takecare.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
