package com.meadowspace.meadowSpaceProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.MultimediaProperty;

@Repository
public interface IMultimediaPropertyRepository extends JpaRepository<MultimediaProperty, String> {

}
