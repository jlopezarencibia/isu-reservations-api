package com.isucorp.reservationsapi.repositories;

import com.isucorp.reservationsapi.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    /**
     * Finds a client by it's name
     *
     * @param term part of the name to ask for
     * @return a ClientEntity or a List of ClientEntity matching with the given name
     * */
    @Query(value = "SELECT * FROM client " +
            "WHERE LOWER(name) LIKE LOWER(:term)", nativeQuery = true)
    List<ClientEntity> findByName(@Param("term") String term);

}
