package com.vijay.mapper;

import com.vijay.modal.Subscription;
import com.vijay.modal.SubscriptionPlan;
import com.vijay.modal.User;
import com.vijay.payload.dto.SubscriptionDTO;

import java.time.LocalDate;
import java.util.List;

public class SubscriptionMapper {

    public static SubscriptionDTO toDTO(Subscription subscription) {
        if (subscription == null) return null;

        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());

        // map user
        if (subscription.getUser() != null) {
            dto.setUserId(subscription.getUser().getId());
        }

        // map plan
        if (subscription.getPlan() != null) {
            dto.setCurrency(subscription.getPlan().getCurrency());
        }

        dto.setPlanName(subscription.getPlanName());
        dto.setPlanCode(subscription.getPlanCode());
        dto.setPrice(subscription.getPrice());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setIsActive(subscription.getIsActive());
        dto.setMaxBooksAllowed(subscription.getMaxBooksAllowed());
        dto.setMaxDaysPerBook(subscription.getMaxDaysForBook());
        dto.setAutoRenew(subscription.getAutoReview());
        dto.setCancelledAt(subscription.getCancelledAt());
        dto.setCancellationReason(subscription.getCancellationReason());
        dto.setNotes(subscription.getNotes());
        dto.setDaysRemaining(subscription.getDaysRemaining());
        dto.setIsValid(subscription.isValid());
        dto.setIsExpired(subscription.isExpired());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdatedAt(subscription.getUpdatedAt());

        return dto;
    }

    public static Subscription toEntity(SubscriptionDTO dto, User user, SubscriptionPlan plan) {
        if (dto == null) return null;

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .startDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now())
                .autoReview(dto.getAutoRenew() != null ? dto.getAutoRenew() : false)
                .notes(dto.getNotes())
                .isActive(true)
                .build();

        subscription.initializeFromPlan();
        return subscription;
    }

    public static List<SubscriptionDTO> toDTOList(List<Subscription> subscriptions) {
        if (subscriptions == null || subscriptions.isEmpty()) return List.of();

        return subscriptions.stream()
                .map(SubscriptionMapper::toDTO)
                .toList();
    }
}