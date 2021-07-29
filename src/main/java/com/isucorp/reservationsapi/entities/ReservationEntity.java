package com.isucorp.reservationsapi.entities;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ApiModel
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint")
    private Integer id;

    private String location;
    private String date;
    private boolean favorite;
    private Integer ranking;
    @Column(length = 1000000000)
    private String image;

    @ManyToOne(targetEntity = ClientEntity.class)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClientEntity client;

    public void  setData(String location, String date, boolean favorite, Integer ranking, String image) {
        this.location = location;
        this.date = date;
        this.favorite = favorite;
        this.ranking = ranking;
        this.image = image;
    }
}
