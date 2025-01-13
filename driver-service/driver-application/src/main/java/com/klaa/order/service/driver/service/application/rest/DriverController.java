package com.klaa.order.service.driver.service.application.rest;

import com.klaa.order.system.driver.service.domain.dto.message.DriverRequest;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;
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
    @PostMapping("/approve")
    public ResponseEntity<DriverResponse> approveOrder(@RequestBody DriverRequest driverRequest){
        DriverResponse driverResponse = driverApplicationService.approveOrder(driverRequest);
        return ResponseEntity.ok(driverResponse);
    }
    @PostMapping("/reject")
    public ResponseEntity<DriverResponse> rejectOrder(@RequestBody DriverRequest driverRequest){
        DriverResponse driverResponse = driverApplicationService.rejectOrder(driverRequest);
        return ResponseEntity.ok(driverResponse);
    }


}
