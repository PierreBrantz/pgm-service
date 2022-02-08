package com.cinqc.maraichage.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

}
