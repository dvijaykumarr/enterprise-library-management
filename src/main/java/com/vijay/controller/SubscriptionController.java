package com.vijay.controller;

import com.vijay.payload.dto.SubscriptionDTO;
import com.vijay.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionDTO subscriptionDTO) throws Exception {
        SubscriptionDTO dto = subscriptionService.subscribe(subscriptionDTO);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("/admin")
    public ResponseEntity<?> getALlSubscriptions(){

        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<SubscriptionDTO> dtoList = subscriptionService.getAllSubscriptions(pageable);

        return ResponseEntity.ok(dtoList);
    }


}
