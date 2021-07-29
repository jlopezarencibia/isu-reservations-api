package com.isucorp.reservationsapi.services;

import com.isucorp.reservationsapi.entities.ClientEntity;
import com.isucorp.reservationsapi.entities.ReservationEntity;
import com.isucorp.reservationsapi.exceptions.ClientNotFoundException;
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

    public List<ClientEntity> getAll() {
        return clientRepository.findAll();
    }

    /**
     * Returns all the clients in the database paged
     *
     * @param options Options for pagination
     * @return All clients in a page
     * */
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

    public Long count() {
        return this.clientRepository.count();
    }

    public ClientEntity create(Client client) {
//        System.out.println(client.getType());
//        System.out.println(client.toClient().getType());
        return clientRepository.save(client.toClient());
    }

    public ClientEntity deleteById(Integer id) throws ClientNotFoundException {
        Optional<ClientEntity> client = findById(id);
        client.ifPresent(c -> clientRepository.deleteById(c.getId()));
        return client.orElseThrow(ClientNotFoundException::new);
    }

    public Optional<ClientEntity> findById(Integer id) {
        return clientRepository.findById(id);
    }

    public ClientEntity update(Integer id, Client client) throws ClientNotFoundException {
        Optional<ClientEntity> clientEntity = findById(id);
        if (clientEntity.isPresent()) {
            clientEntity.get().setName(client.getName());
            clientEntity.get().setType(client.getType());
            clientEntity.get().setPhone(client.getPhone());
            clientEntity.get().setBirthDate(client.getBirthDate());
            clientEntity.get().setDescription(client.getDescription());
            return clientRepository.save(clientEntity.get());
        } else {
            throw new ClientNotFoundException();
        }
    }

    public ReservationEntity reserve(Reservation reservation, Integer id) {
        ReservationEntity newReservation = this.reservationService.create(reservation);
        ClientEntity client = findById(id).orElseThrow();
        newReservation.setClient(client);
        return reservationService.save(newReservation);

//        client.ifPresent(c -> c.addReservation(newReservation));
//        ClientEntity client = findById(id).orElseThrow();
//        ReservationEntity reservationEntity = new ReservationEntity();
//        reservationEntity.setData(reservation.getLocation(), reservation.getDate(), reservation.isFavorite(), reservation.getRanking());
//        reservationEntity.setClient(client);
//        reservationService.save(reservationEntity);
//        return reservationEntity;
    }

    public List<ClientEntity> findByName(String term) {
//        System.out.println('%' + term + '%');
        return this.clientRepository.findByName(asPattern(term));
    }

    private String asPattern(String term) {
        return '%' + term + '%';
    }

    public ClientEntity removeById(Integer id) {
        var client = findById(id);
        if (client.isPresent()) {
            this.clientRepository.deleteById(id);
            return client.get();
        }
        return null;
    }
}
