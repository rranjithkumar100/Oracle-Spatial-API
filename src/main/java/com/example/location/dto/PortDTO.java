package com.example.location.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortDTO {
    private int id;

    private String name;
    private double latitude;
    private double longitude;

    @JsonIgnore
    private int rangeInMeters;
    private int noOfResults;


}
