package com.akshat.trackops.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshat.trackops.delivery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    boolean existsByTitleAndPickupLocationAndDropLocation(String title, String pickupLocation, String dropLocation);
}
