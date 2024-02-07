package com.meadowspace.meadowSpaceProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.MultimediaOpinions;

@Repository
public interface IMultimediaOpinionRepository extends JpaRepository<MultimediaOpinions, String> {

}
