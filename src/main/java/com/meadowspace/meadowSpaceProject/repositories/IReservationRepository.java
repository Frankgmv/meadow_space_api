package com.meadowspace.meadowSpaceProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meadowspace.meadowSpaceProject.entity.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, String> {

}
