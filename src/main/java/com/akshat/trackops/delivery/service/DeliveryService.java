package com.akshat.trackops.delivery.service;

import java.util.List;

import com.akshat.trackops.delivery.dto.CreateDeliveryRequest;
import com.akshat.trackops.delivery.dto.DeliveryResponse;
import com.akshat.trackops.delivery.dto.UpdateRequestStatus;

public interface DeliveryService {
    DeliveryResponse createDelivery(CreateDeliveryRequest request) throws Exception;
    List<DeliveryResponse> getDeliveries();
    DeliveryResponse deleteDelivery(Long id) throws Exception;
    DeliveryResponse assignAgent(Long agentId, Long deliveryId) throws Exception;
    List<DeliveryResponse> getAgentDeliveries();
    DeliveryResponse updateDeliveryStatus(Long deliveryId, UpdateRequestStatus status) throws Exception;
}
