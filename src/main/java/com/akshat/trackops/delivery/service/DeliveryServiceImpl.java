package com.akshat.trackops.delivery.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akshat.trackops.delivery.dto.CreateDeliveryRequest;
import com.akshat.trackops.delivery.dto.DeliveryResponse;
import com.akshat.trackops.delivery.entity.Delivery;
import com.akshat.trackops.delivery.entity.DeliveryStatus;
import com.akshat.trackops.delivery.repository.DeliveryRepository;
import com.akshat.trackops.user.entity.Role;
import com.akshat.trackops.user.entity.User;
import com.akshat.trackops.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;

    @Override
    public DeliveryResponse createDelivery(CreateDeliveryRequest request) throws Exception {

        if (deliveryRepository.existsByTitleAndPickupLocationAndDropLocation(request.getTitle(),
                request.getPickupLocation(), request.getDropLocation())) {
            throw new Exception("Delivery already exists!!");
        } else {
            Delivery delivery = new Delivery();
            // saving delivery in DB
            delivery.setTitle(request.getTitle());
            delivery.setCreatedAt(LocalDateTime.now());
            delivery.setDropLocation(request.getDropLocation());
            delivery.setPickupLocation(request.getPickupLocation());
            delivery.setStatus(DeliveryStatus.PENDING);
            Delivery savedDelivery = deliveryRepository.save(delivery);

            // making delivery response
            DeliveryResponse response = new DeliveryResponse();
            response.setId(savedDelivery.getId());
            response.setTitle(savedDelivery.getTitle());
            response.setDropLocation(savedDelivery.getDropLocation());
            response.setPickupLocation(savedDelivery.getPickupLocation());
            response.setStatus(savedDelivery.getStatus().name());
            response.setCreatedAt(savedDelivery.getCreatedAt());
            return response;
        }
    }

    @Override
    public List<DeliveryResponse> getDeliveries() {
        List<DeliveryResponse> deliveries = new ArrayList<>();
        for (Delivery delivery : deliveryRepository.findAll()) {
            DeliveryResponse response = new DeliveryResponse();
            response.setCreatedAt(delivery.getCreatedAt());
            response.setDropLocation(delivery.getDropLocation());
            response.setId(delivery.getId());
            response.setPickupLocation(delivery.getPickupLocation());
            response.setStatus(delivery.getStatus().name());
            response.setTitle(delivery.getTitle());
            if (delivery.getAssignedAgent() != null) {
                
                response.setAssignedAgentEmail(delivery.getAssignedAgent().getEmail());
                response.setAssignedAgentId(delivery.getAssignedAgent().getId());
                response.setAssignedAgentName(delivery.getAssignedAgent().getName());
            }

            deliveries.add(response);
        }
        return deliveries;
    }

    @Override
    public DeliveryResponse deleteDelivery(Long id) throws Exception {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isEmpty()) {
            throw new Exception("Delivery not found");
        } else {
            DeliveryResponse response = new DeliveryResponse();
            response.setCreatedAt(delivery.get().getCreatedAt());
            response.setDropLocation(delivery.get().getDropLocation());
            response.setId(delivery.get().getId());
            response.setPickupLocation(delivery.get().getPickupLocation());
            response.setStatus(delivery.get().getStatus().name());
            response.setTitle(delivery.get().getTitle());
            deliveryRepository.deleteById(id);
            return response;
        }

    }

    @Override
    public DeliveryResponse assignAgent(Long agentId, Long deliveryId) throws Exception {
        User user = userRepository.findById(agentId).orElseThrow(() -> new Exception("User does not exist"));
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Delivery does not exist"));
        if (user.getRole() == Role.AGENT ) {
            if (delivery.getAssignedAgent() != null) {
                throw new Exception("Delivery is already assigned");
            }
            delivery.setAssignedAgent(user);
            delivery.setStatus(DeliveryStatus.ASSIGNED);
            deliveryRepository.save(delivery);
            DeliveryResponse response = new DeliveryResponse();
            response.setCreatedAt(delivery.getCreatedAt());
            response.setDropLocation(delivery.getDropLocation());
            response.setId(delivery.getId());
            response.setPickupLocation(delivery.getPickupLocation());
            response.setStatus(delivery.getStatus().name());
            response.setTitle(delivery.getTitle());
            response.setAssignedAgentEmail(delivery.getAssignedAgent().getEmail());
            response.setAssignedAgentId(delivery.getAssignedAgent().getId());
            response.setAssignedAgentName(delivery.getAssignedAgent().getName());
            return response;
        } else {
            throw new Exception("Role type is not AGENT");
        }

    }

}
