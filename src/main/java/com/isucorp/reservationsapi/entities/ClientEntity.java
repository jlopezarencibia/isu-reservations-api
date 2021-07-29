package com.isucorp.reservationsapi.entities;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ApiModel
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint")
    private Integer id;
    private String name;
    private String type;
    private String phone;
    private String birthDate;
    @Column(length = 1000000000)
    private String description;

//    @OneToMany(targetEntity = ReservationEntity.class, orphanRemoval = true, mappedBy = "client")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    public Set<ReservationEntity> reservations = new HashSet<>();

//    public void addReservation(ReservationEntity reservation) {
//        this.reservations.add(reservation);
//    }

    public void setData(String name, String type, String phone, String birthDate, String description) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.birthDate = birthDate;
        this.description = description;
    }

}
