package com.isucorp.reservationsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSort {

    @JsonProperty()
    private int page;

    @JsonProperty()
    private int pageSize;

    @JsonProperty()
    private String sortBy;

    @JsonProperty()
    private String sortDirection;
}
