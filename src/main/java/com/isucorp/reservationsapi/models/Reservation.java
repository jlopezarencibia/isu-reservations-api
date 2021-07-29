package com.isucorp.reservationsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isucorp.reservationsapi.entities.ReservationEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Reservation {

    @JsonProperty()
    private String location;

    @JsonProperty()
    private String date;

    @JsonProperty()
    private boolean favorite;

    @JsonProperty()
    private Integer ranking;

    @JsonProperty()
    private String image;

    public ReservationEntity toReservation() {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setData(location, date, favorite, ranking, image);
        return reservation;
    }
}
