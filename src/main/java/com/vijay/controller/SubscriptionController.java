package com.vijay.controller;

import com.vijay.payload.dto.SubscriptionDTO;
import com.vijay.payload.response.ApiResponse;
import com.vijay.service.SubscriptionService;
import com.vijay.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionDTO subscriptionDTO) throws Exception {
        SubscriptionDTO dto = subscriptionService.subscribe(subscriptionDTO);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/user/active")
    public ResponseEntity<?> getUsersActiveSubscriptions() throws Exception { // ✅ no @RequestParam
        Long userId = userService.getCurrentUserEntity().getId(); // ✅ from JWT
        SubscriptionDTO dto = subscriptionService.getUsersActiveSubscription(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getALlSubscriptions(){

        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<SubscriptionDTO> dtoList = subscriptionService.getAllSubscriptions(pageable);

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin/deactivate-expired")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateExpiredSubscriptions(){

        subscriptionService.deactivateExpiredSubscriptions();

        ApiResponse apiResponse = new ApiResponse("task done", true);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(@PathVariable Long subscriptionId,
                                                @RequestParam(required = false) String reason){
        SubscriptionDTO subscriptionDTO = subscriptionService.cancelSubscription(subscriptionId, reason);
        return ResponseEntity.ok(subscriptionDTO);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateSubscription(
            @RequestParam Long subscriptionId,
            @RequestParam Long paymentId
    ){
        SubscriptionDTO subscriptionDTO = subscriptionService.activateSubscription(subscriptionId, paymentId);
        return ResponseEntity.ok(subscriptionDTO);
    }




}
