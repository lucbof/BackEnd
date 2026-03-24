package com.broers.users.repositories;

import com.broers.users.enums.RoleList;
import com.broers.users.repositories.entities.RolEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Integer> {

    Optional<RolEntity> findByName(RoleList name);
}
