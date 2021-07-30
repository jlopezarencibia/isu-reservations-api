package com.isucorp.reservationsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isucorp.reservationsapi.entities.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

    @JsonProperty()
    private String name;

    @JsonProperty()
    private String type;

    @JsonProperty()
    private String phone;

    @JsonProperty()
    private String birthDate;

    @JsonProperty()
    private String description;

    /**
     * Converts the object to a <b>ClientEntity</b>
     *
     * @return a ClientEntity instance without Id
     * */
    public ClientEntity toClient() {
        ClientEntity client = new ClientEntity();
        client.setData(name, type, phone, birthDate, description);
        return client;
    }

}


