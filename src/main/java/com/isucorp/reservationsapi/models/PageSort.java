package com.isucorp.reservationsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
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
