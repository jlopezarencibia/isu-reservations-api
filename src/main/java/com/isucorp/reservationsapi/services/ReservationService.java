package com.isucorp.reservationsapi.services;

import com.isucorp.reservationsapi.entities.ReservationEntity;
import com.isucorp.reservationsapi.exceptions.ReservationNotFoundException;
import com.isucorp.reservationsapi.models.PageSort;
import com.isucorp.reservationsapi.models.Reservation;
import com.isucorp.reservationsapi.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationEntity create(Reservation reservation) {
        return reservationRepository.save(reservation.toReservation());
    }

    public List<ReservationEntity> getAll() {
        return this.reservationRepository.findAll();
    }

    public List<ReservationEntity> getPaged(PageSort options) {
        return this.reservationRepository.findAll(
                PageRequest.of(
                        options.getPage(),
                        options.getPageSize(),
                        options.getSortDirection().equals("ASC") ?
                                Sort.by(options.getSortBy()).ascending() :
                                Sort.by(options.getSortBy()).descending())
        ).toList();
    }

    public Long count() {
        return this.reservationRepository.count();
    }

    public Optional<ReservationEntity> findById(Integer id) {
        return this.reservationRepository.findById(id);
    }

    public ReservationEntity toggleFavorite(Integer id) throws ReservationNotFoundException {
        Optional<ReservationEntity> reservation = findById(id);
        if (reservation.isPresent()) {
            reservation.get().setFavorite(!reservation.get().isFavorite());
            return reservationRepository.save(reservation.get());
        }
        throw new ReservationNotFoundException();
    }

    public ReservationEntity update(Integer id, ReservationEntity reservation) throws ReservationNotFoundException {
        Optional<ReservationEntity> reservationEntity = findById(id);
        if (reservationEntity.isPresent()) {
            reservationEntity.get().setFavorite(reservation.isFavorite());
            reservationEntity.get().setDate(reservation.getDate());
            reservationEntity.get().setLocation(reservation.getLocation());
            reservationEntity.get().setRanking(reservation.getRanking());
            reservationEntity.get().setImage(reservation.getImage());
            return reservationRepository.save(reservationEntity.get());
        } else {
            throw new ReservationNotFoundException();
        }
    }

    public ReservationEntity removeById(Integer id) {
        var reservation = findById(id);
        if (reservation.isPresent()) {
            this.reservationRepository.deleteById(id);
            return reservation.get();
        } else {
            return null;
        }
    }

    public ReservationEntity save(ReservationEntity reservationEntity) {
        return this.reservationRepository.save(reservationEntity);
    }
}
