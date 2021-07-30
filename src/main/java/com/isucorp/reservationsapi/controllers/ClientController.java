package com.isucorp.reservationsapi.controllers;

import com.isucorp.reservationsapi.entities.ClientEntity;
import com.isucorp.reservationsapi.entities.ReservationEntity;
import com.isucorp.reservationsapi.exceptions.ClientNotFoundException;
import com.isucorp.reservationsapi.models.Client;
import com.isucorp.reservationsapi.models.PageSort;
import com.isucorp.reservationsapi.models.Reservation;
import com.isucorp.reservationsapi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(
            ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping()
    public @ResponseBody
    ResponseEntity<List<ClientEntity>> all() {
        return ResponseEntity.ok(this.clientService.getAll());
    }

    @GetMapping("/paged")
    public @ResponseBody
    ResponseEntity<List<ClientEntity>> getPaged(@io.swagger.v3.oas.annotations.parameters.RequestBody PageSort options) {
        return ResponseEntity.ok(this.clientService.getPaged(options));
    }

    @GetMapping("/count")
    public @ResponseBody
    Long amount() {
        return this.clientService.count();
    }

    @PostMapping()
    public @ResponseBody
    ResponseEntity<ClientEntity> add(@RequestBody Client client) {
        return ResponseEntity.ok(this.clientService.create(client));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ClientEntity> remove(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(this.clientService.deleteById(id));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ClientEntity> findById(@PathVariable Integer id) {
        var clientEntity = clientService.findById(id);
        return clientEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/reserve/{id}")
    public @ResponseBody
    ResponseEntity<ReservationEntity> reserve(@RequestBody Reservation reservation, @PathVariable Integer id) {
        return ResponseEntity.ok(this.clientService.reserve(reservation, id));
    }

    @PatchMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ClientEntity> edit(@PathVariable Integer id, @RequestBody Client client) {
        try {
            return ResponseEntity.ok(this.clientService.update(id, client));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/find/{term}")
    public @ResponseBody
    ResponseEntity<List<ClientEntity>> findByName(@PathVariable String term) {
        return ResponseEntity.ok(this.clientService.findByName(term));
    }

    @DeleteMapping("/remove/{id}")
    public @ResponseBody
    ResponseEntity<ClientEntity> removeById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.clientService.removeById(id));
    }
}
