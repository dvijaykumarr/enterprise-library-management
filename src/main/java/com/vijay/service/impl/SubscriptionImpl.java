package com.vijay.service.impl;

import com.vijay.exception.SubscriptionException;
import com.vijay.mapper.SubscriptionMapper;
import com.vijay.modal.Subscription;
import com.vijay.modal.SubscriptionPlan;
import com.vijay.modal.User;
import com.vijay.payload.dto.SubscriptionDTO;
import com.vijay.repository.SubscriptionPlanRepository;
import com.vijay.repository.SubscriptionRepository;
import com.vijay.service.SubscriptionService;
import com.vijay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final SubscriptionPlanRepository subscriptionPlanRepository;


    @Override
    public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception {

        User user = userService.getCurrentUserEntity();

        SubscriptionPlan plan = subscriptionPlanRepository.findById(subscriptionDTO.getId()).orElseThrow(
                ()-> new Exception("plan not found!")
        );

        Subscription subscription = SubscriptionMapper.toEntity(subscriptionDTO);
        subscription.initializeFromPlan();

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        //create paymetn (todo) later
        return SubscriptionMapper.toDTO(savedSubscription);
    }

    @Override
    public SubscriptionDTO getUsersActiveSubscription(Long userId) {

        Subscription subscription = subscriptionRepository
                .findActiveSubscriptionByUserId(userId, LocalDate.now()) // ✅ pass today
                .orElseThrow(() -> new SubscriptionException("no active subscription found"));

        return SubscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(()->new SubscriptionException("Subscription not found with ID: "+ subscriptionId));

        if(!subscription.getIsActive()){
            throw new SubscriptionException("Subscription is already inactive");
        }

        //mark as cancelled
        subscription.setIsActive(false);
        subscription.setCancelledAt(LocalDateTime.now());
        subscription.setCancellationReason(reason != null ? reason: "Cancelled by user");

        subscription = subscriptionRepository.save(subscription);

        return SubscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(
                        () -> new SubscriptionException("Subscription is not found by id")
                );

        // verify payment (todo) later
        subscription.setIsActive(true);

        subscription = subscriptionRepository.save(subscription);

        return SubscriptionMapper.toDTO(subscription);
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {

        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return SubscriptionMapper.toDTOList(subscriptions);
    }

    @Override
    public void deactivateExpiredSubscriptions(Long userId) {
        List<Subscription> expiredSubscriptions = subscriptionRepository.findByExpiredActiveSubscriptions(LocalDate.now());

        for(Subscription subscription : expiredSubscriptions){
            subscription.setIsActive(false);
            subscriptionRepository.save(subscription);
        }
    }
}
