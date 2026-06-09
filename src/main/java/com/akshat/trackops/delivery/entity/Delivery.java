package com.akshat.trackops.delivery.entity;

import java.time.LocalDateTime;

import com.akshat.trackops.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String pickupLocation;  
    private String dropLocation;
    private DeliveryStatus status;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "assigned_agent_id")
    private User assignedAgent;
}
