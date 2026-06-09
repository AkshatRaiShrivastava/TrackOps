package com.akshat.trackops.delivery.controller;

import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.trackops.common.ApiResponse;
import com.akshat.trackops.delivery.dto.CreateDeliveryRequest;
import com.akshat.trackops.delivery.dto.DeliveryResponse;
import com.akshat.trackops.delivery.service.DeliveryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/deliveries")
@AllArgsConstructor
public class DeliveryController {
    
    private final DeliveryService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<DeliveryResponse>> createDelivery(@RequestBody CreateDeliveryRequest request){
        try{
            DeliveryResponse response = service.createDelivery(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<DeliveryResponse>(true, "Delivery created successfully", response));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new @Nullable ApiResponse<DeliveryResponse>(false, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getDeliveries(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDeliveries());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DeliveryResponse>> deleteDelivery(@PathVariable Long id){
        try{
            DeliveryResponse response = service.deleteDelivery(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<DeliveryResponse>(true, "Delivery deleted successfully", response));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{deliveryId}/assign/{agentId}")
    public ResponseEntity<ApiResponse<DeliveryResponse>> assignDeliveryToAgent(@PathVariable Long deliveryId, @PathVariable Long agentId){
        try{
            DeliveryResponse response = service.assignAgent(agentId, deliveryId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Agent assigned successfully", response));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    
}
