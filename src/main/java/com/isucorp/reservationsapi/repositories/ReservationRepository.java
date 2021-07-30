package com.isucorp.reservationsapi.repositories;

import com.isucorp.reservationsapi.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {
}
