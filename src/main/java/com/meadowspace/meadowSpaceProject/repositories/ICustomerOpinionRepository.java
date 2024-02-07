package com.meadowspace.meadowSpaceProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.CustomerOpinion;

@Repository
public interface ICustomerOpinionRepository extends JpaRepository<CustomerOpinion, String> {

}
