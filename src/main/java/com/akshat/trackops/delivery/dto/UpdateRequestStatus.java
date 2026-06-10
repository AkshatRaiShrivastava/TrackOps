package com.akshat.trackops.delivery.dto;

import com.akshat.trackops.delivery.entity.DeliveryStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequestStatus {
    @NotNull
    private DeliveryStatus status;
}
