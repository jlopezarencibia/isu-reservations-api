package com.isucorp.reservationsapi.entities;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

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

    public void setData(String name, String type, String phone, String birthDate, String description) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.birthDate = birthDate;
        this.description = description;
    }

}
