package com.vijay.mapper;

import com.vijay.modal.SubscriptionPlan;
import com.vijay.payload.dto.SubscriptionPlanDTO;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {

    public static SubscriptionPlanDTO toDTO(SubscriptionPlan plan) {
        if (plan == null) return null;

        return SubscriptionPlanDTO.builder()
                .id(plan.getId())
                .planCode(plan.getPlanCode())
                .name(plan.getName())
                .description(plan.getDescription())
                .durationDays(plan.getDurationDays())
                .price(plan.getPrice())
                .currency(plan.getCurrency())
                .maxBooksAllowed(plan.getMaxBooksAllowed())
                .maxDaysPerBook(plan.getMaxDaysPerBook())
                .displayOrder(plan.getDisplayOrder())
                .isActive(plan.getIsActive())
                .isFeatured(plan.getIsFeatured())
                .badgeText(plan.getBadgeText())
                .adminNotes(plan.getAdminNotes())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .createdBy(plan.getCreatedBy())
                .updatedBy(plan.getUpdatedBy())
                .build();
    }

    public static SubscriptionPlan toEntity(SubscriptionPlanDTO dto) {
        if (dto == null) return null;

        return SubscriptionPlan.builder()
                .id(dto.getId())
                .planCode(dto.getPlanCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .durationDays(dto.getDurationDays())
                .price(dto.getPrice())
                .currency(dto.getCurrency() != null ? dto.getCurrency() : "INR")
                .maxBooksAllowed(dto.getMaxBooksAllowed())
                .maxDaysPerBook(dto.getMaxDaysPerBook())
                .displayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0)
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .isFeatured(dto.getIsFeatured() != null ? dto.getIsFeatured() : false)
                .badgeText(dto.getBadgeText())
                .adminNotes(dto.getAdminNotes())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }

    public static void updateEntityFromDTO(SubscriptionPlanDTO dto, SubscriptionPlan plan) {
        if (dto == null || plan == null) return;

        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setDurationDays(dto.getDurationDays());
        plan.setPrice(dto.getPrice());
        plan.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : plan.getCurrency());
        plan.setMaxBooksAllowed(dto.getMaxBooksAllowed());
        plan.setMaxDaysPerBook(dto.getMaxDaysPerBook());
        plan.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : plan.getDisplayOrder());
        plan.setIsFeatured(dto.getIsFeatured() != null ? dto.getIsFeatured() : plan.getIsFeatured());
        plan.setBadgeText(dto.getBadgeText());
        plan.setAdminNotes(dto.getAdminNotes());
        // Note: planCode, isActive, createdBy handled separately
    }
}