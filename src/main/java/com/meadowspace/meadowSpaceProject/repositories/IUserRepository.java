package com.meadowspace.meadowSpaceProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	@Query("SELECT us FROM User us WHERE us.email = :email")
    public List<User> buscarPorEmail(@Param("email") String email);
}
