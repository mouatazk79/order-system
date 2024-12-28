package com.klaa.order.service.driver.service.application.rest;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.reject.DriverRejectResponse;
import com.klaa.order.system.driver.service.domain.ports.input.service.DriverApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class DriverController {
    private final DriverApplicationService driverApplicationService;
    @PostMapping("/reject")
    public ResponseEntity<DriverRejectResponse> rejectOrder(@RequestBody DriverRequest driverRequest){
        DriverRejectResponse driverRejectResponse= driverApplicationService.rejectOrder(driverRequest);
        return ResponseEntity.ok(driverRejectResponse);
    }


}
