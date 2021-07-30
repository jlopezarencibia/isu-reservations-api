package com.isucorp.reservationsapi.services;

import com.isucorp.reservationsapi.entities.ClientEntity;
import com.isucorp.reservationsapi.entities.ReservationEntity;
import com.isucorp.reservationsapi.models.Client;
import com.isucorp.reservationsapi.models.PageSort;
import com.isucorp.reservationsapi.models.Reservation;
import com.isucorp.reservationsapi.repositories.ClientRepository;
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
public class ClientService {

    private final ClientRepository clientRepository;
    private final ReservationService reservationService;

    @Autowired
    public ClientService(ClientRepository clientRepository, ReservationService reservationService) {
        this.clientRepository = clientRepository;
        this.reservationService = reservationService;
    }

    /**
     * Get all Clients
     *
     * @return a list of Clients
     */
    public List<ClientEntity> getAll() {
        return clientRepository.findAll();
    }

    /**
     * Get all the clients in a specific page and with defined sorting options
     *
     * @param options Options for pagination and sort
     * @return All clients ordered in a page
     */
    public List<ClientEntity> getPaged(PageSort options) {
        return this.clientRepository.findAll(
                PageRequest.of(
                        options.getPage(),
                        options.getPageSize(),
                        options.getSortDirection().equals("ASC") ?
                                Sort.by(options.getSortBy()).ascending() :
                                Sort.by(options.getSortBy()).descending())
        ).toList();
    }

    /**
     * How many clients
     *
     * @return total of clients
     */
    public Long count() {
        return this.clientRepository.count();
    }

    /**
     * Create a new client
     *
     * @param client the Client data without Id
     * @return the created Client
     */
    public ClientEntity create(Client client) {
        return clientRepository.save(client.toClient());
    }

    /**
     * Delete a Client with provided Id
     *
     * @param id the Id of the Client to remove
     * @return the removed Client
     */
    public ClientEntity deleteById(Integer id) throws NoSuchElementException {
        ClientEntity client = findById(id).orElseThrow();
        this.clientRepository.deleteById(id);
        return client;
    }

    /**
     * Find a Client with the provided Id
     *
     * @param id the Id of the Client to get
     * @return the Client
     */
    public Optional<ClientEntity> findById(Integer id) {
        return clientRepository.findById(id);
    }

    /**
     * Modify a Client
     *
     * @param id     the Id of the Client to update
     * @param client the Client new information
     * @return the updated Client
     */
    public ClientEntity update(Integer id, Client client) throws NoSuchElementException {
        ClientEntity clientEntity = findById(id).orElseThrow();
        clientEntity.setData(
                client.getName(),
                client.getType(),
                client.getPhone(),
                client.getBirthDate(),
                client.getDescription()
        );
        return clientRepository.save(clientEntity);
    }

    /**
     * Add a reservation to a client
     *
     * @param id          the Id of the Client that owns the reservation
     * @param reservation the Reservation information
     * @return the new Reservation
     * @throws java.util.NoSuchElementException when there's no Client with the given Id
     */
    public ReservationEntity reserve(Reservation reservation, Integer id) {
        ReservationEntity newReservation = this.reservationService.create(reservation);
        ClientEntity client = findById(id).orElseThrow();
        newReservation.setClient(client);
        return reservationService.save(newReservation);
    }

    /**
     * Find a Client with the provided name
     *
     * @param term the partial text of the Client name to get
     * @return all the possible Clients
     */
    public List<ClientEntity> findByName(String term) {
        return this.clientRepository.findByName(asPattern(term));
    }

    /**
     * Convert the term into a pattern to search
     *
     * @param term the part of the name of the Client
     * @return the search pattern
     */
    private String asPattern(String term) {
        return '%' + term + '%';
    }

    /**
     * Deletes a client
     *
     * @param id the Id of the Client to delete
     * @return the deleted Client
     */
    public ClientEntity removeById(Integer id) {
        ClientEntity client = findById(id).orElseThrow();
        this.clientRepository.deleteById(id);
        return client;
    }
}
