package com.akshat.trackops.delivery.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryResponse {
    private Long id;
    private String title;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private LocalDateTime createdAt;
    private String assignedAgentName;
    private String assignedAgentEmail;
    private Long assignedAgentId;
}
