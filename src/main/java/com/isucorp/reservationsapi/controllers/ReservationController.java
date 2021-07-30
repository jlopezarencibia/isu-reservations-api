package com.isucorp.reservationsapi.controllers;

import com.isucorp.reservationsapi.entities.ReservationEntity;
import com.isucorp.reservationsapi.exceptions.ReservationNotFoundException;
import com.isucorp.reservationsapi.models.PageSort;
import com.isucorp.reservationsapi.models.Reservation;
import com.isucorp.reservationsapi.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public @ResponseBody
    ResponseEntity<List<ReservationEntity>> all() {
        return ResponseEntity.ok(this.reservationService.getAll());
    }

    @GetMapping("/paged")
    public @ResponseBody
    ResponseEntity<List<ReservationEntity>> paged(@io.swagger.v3.oas.annotations.parameters.RequestBody PageSort options) {
         return ResponseEntity.ok(this.reservationService.getPaged(options));
    }

    @GetMapping("/count")
    public @ResponseBody
    Long count() {
        return this.reservationService.count();
    }

    @PostMapping()
    public @ResponseBody
    ReservationEntity create(@io.swagger.v3.oas.annotations.parameters.RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ReservationEntity> findById(@PathVariable Integer id) {
        var reservationEntity = reservationService.findById(id);
        return reservationEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/favorite/{id}")
    public @ResponseBody
    ResponseEntity<ReservationEntity> toggleFavorite(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(this.reservationService.toggleFavorite(id));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PatchMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ReservationEntity> update(@PathVariable Integer id, @RequestBody ReservationEntity reservation) {
        try {
//            System.out.println("Controller param location: " + reservation.getLocation());
            return ResponseEntity.ok(this.reservationService.update(id, reservation));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ReservationEntity> removeById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.reservationService.removeById(id));
    }
}
