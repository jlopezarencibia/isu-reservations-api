package com.isucorp.reservationsapi.services;

import com.isucorp.reservationsapi.entities.ReservationEntity;
import com.isucorp.reservationsapi.models.PageSort;
import com.isucorp.reservationsapi.models.Reservation;
import com.isucorp.reservationsapi.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Create a new Reservation
     *
     * @param reservation the Reservation data without Id
     * @return the created Reservation
     */
    public ReservationEntity create(Reservation reservation) {
        return reservationRepository.save(reservation.toReservation());
    }

    /**
     * Get all Reservations
     *
     * @return a list of Reservations
     */
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

    /**
     * How many Reservations
     *
     * @return total of Reservations
     */
    public Long count() {
        return this.reservationRepository.count();
    }

    /**
     * Find a Reservation with the provided Id
     *
     * @param id the Id of the Reservation to get
     * @return the Reservation
     */
    public Optional<ReservationEntity> findById(Integer id) {
        return this.reservationRepository.findById(id);
    }

    /**
     * Toggles the Favorite status of the Reservation with the given Id
     *
     * @param id the Id of the Reservation to toggle Favorite
     * @return the updated Reservation
     */
    public ReservationEntity toggleFavorite(Integer id) throws NoSuchElementException {
        ReservationEntity reservation = findById(id).orElseThrow();
        reservation.setFavorite(!reservation.isFavorite());
        return reservationRepository.save(reservation);
    }

    /**
     * Modify a Reservation
     *
     * @param id     the Id of the Reservation to update
     * @param reservation the Client new information
     * @return the updated Client
     */
    public ReservationEntity update(Integer id, ReservationEntity reservation) throws NoSuchElementException {
        ReservationEntity reservationEntity = findById(id).orElseThrow();
        reservationEntity.setData(
                reservation.getLocation(),
                reservation.getDate(),
                reservation.isFavorite(),
                reservation.getRanking(),
                reservation.getImage()
        );
        return reservationRepository.save(reservationEntity);
    }

    /**
     * Delete a Reservation with provided Id
     *
     * @param id the Id of the Reservation to remove
     * @return the removed Reservation
     */
    public ReservationEntity removeById(Integer id) {
        var reservation = findById(id);
        if (reservation.isPresent()) {
            this.reservationRepository.deleteById(id);
            return reservation.get();
        } else {
            return null;
        }
    }

    /**
     * Saves a Reservation to the Database
     *
     * @param reservationEntity the Reservation to save
     * @return the saved Reservation
     */
    public ReservationEntity save(ReservationEntity reservationEntity) {
        return this.reservationRepository.save(reservationEntity);
    }
}
