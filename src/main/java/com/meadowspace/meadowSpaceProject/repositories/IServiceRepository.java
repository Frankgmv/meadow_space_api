package com.meadowspace.meadowSpaceProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Services;

@Repository
public interface IServiceRepository extends JpaRepository<Services, String> {

}
