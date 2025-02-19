package com.klaa.order.system.order.service.application.rest;

import com.klaa.order.system.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.order.service.domain.dto.create.OrderCreateResponse;

import com.klaa.order.system.order.service.domain.dto.reject.RejectOrderResponse;
import com.klaa.order.system.order.service.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.order.service.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody OrderCreateCommand orderCreateCommand){
        log.info("receiving order command {}",orderCreateCommand);
        OrderCreateResponse orderCreateResponse= orderApplicationService.createOrder(orderCreateCommand);
        return ResponseEntity.ok(orderCreateResponse);
    }
    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> trackOrder(@PathVariable("trackingId") UUID trackingId){
        TrackOrderQuery trackOrderQuery= TrackOrderQuery.builder().orderTrackingId(trackingId).build();
        log.info("receiving order tracking query {}",trackOrderQuery);
        TrackOrderResponse trackOrderResponse= orderApplicationService.trackOrder(trackOrderQuery);
        return ResponseEntity.ok(trackOrderResponse);
    }
    @PatchMapping
    public ResponseEntity<RejectOrderResponse> rejectOrder(@RequestBody TrackOrderQuery trackOrderQuery){
        log.info("receiving order rejection query {}",trackOrderQuery);
        RejectOrderResponse rejectOrderResponse= orderApplicationService.rejectOrder(trackOrderQuery);
        return ResponseEntity.ok(rejectOrderResponse);
    }


}
