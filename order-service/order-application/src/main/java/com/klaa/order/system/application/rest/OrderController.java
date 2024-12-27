package com.klaa.order.system.application.rest;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.dto.remove.RejectOrderResponse;
import com.klaa.order.system.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.domain.ports.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<TrackOrderResponse> trackOrder(@RequestBody TrackOrderQuery trackOrderQuery){
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
