package com.vijay.payload.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionPlanDTO {

    private Long id;

    @NotBlank(message = "Plan code is required")
    private String planCode;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Duration in days is required")
    @Positive(message = "Duration must be positive")
    private Integer durationDays;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Long price;

    private String currency;

    @NotNull(message = "Max books allowed is mandatory")
    @Positive(message = "Max Books must be positive")
    private Integer maxBooksAllowed;

    @NotNull(message = "Max days per book is mandatory")
    @Positive(message = "Max days must be positive")
    private Integer maxDaysPerBook;


    private Integer displayOrder;

    private Boolean isActive;
    private Boolean isFeatured;

    private String badgeText;

    private String adminNotes;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private String createdBy;
    private String updatedBy;
}
