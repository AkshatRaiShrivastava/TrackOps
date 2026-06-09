package com.akshat.trackops.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDeliveryRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String pickupLocation;
    @NotBlank
    private String dropLocation;

}