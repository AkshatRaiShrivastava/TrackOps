package com.akshat.trackops.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshat.trackops.delivery.entity.Delivery;
import com.akshat.trackops.user.entity.User;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    boolean existsByTitleAndPickupLocationAndDropLocation(String title, String pickupLocation, String dropLocation);
    List<Delivery> findByAssignedAgent(User user);
}
