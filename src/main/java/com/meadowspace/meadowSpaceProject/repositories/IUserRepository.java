package com.meadowspace.meadowSpaceProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

}
