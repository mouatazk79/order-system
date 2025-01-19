package com.klaa.order.system.driver.service.application.rest;

import com.klaa.order.system.driver.service.domain.dto.approval.ApprovalCommand;
import com.klaa.order.system.driver.service.domain.dto.response.DriverResponse;
import com.klaa.order.system.driver.service.domain.ports.input.service.DriverApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class DriverController {
    private final DriverApplicationService driverApplicationService;
    @PostMapping("/approve")
    public ResponseEntity<DriverResponse> approveOrder(@RequestBody ApprovalCommand approvalCommand){
        log.info("receiving Approval command {}",approvalCommand);
        DriverResponse driverResponse = driverApplicationService.approveOrder(approvalCommand);
        return ResponseEntity.ok(driverResponse);
    }
    @PostMapping("/reject")
    public ResponseEntity<DriverResponse> rejectOrder(@RequestBody ApprovalCommand approvalCommand){
        log.info("receiving Approval command {}",approvalCommand);
        DriverResponse driverResponse = driverApplicationService.rejectOrder(approvalCommand);
        return ResponseEntity.ok(driverResponse);
    }


}
