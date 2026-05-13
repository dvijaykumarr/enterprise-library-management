package com.vijay.repository;

import com.vijay.modal.Subscription;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId " +
            "AND s.isActive = true " +
            "AND s.startDate <= :today " +
            "AND s.endDate >= :today"
    )
    Optional<Subscription> findActiveSubscriptionByUserId(
            @Param("userId") Long userId,
            @Param("today")LocalDate today
            );

    @Query("SELECT s FROM Subscription s WHERE s.isActive=true "+
    "AND s.endDate<:today")
    List<Subscription> findByExpiredActiveSubscriptions(
            @Param("today")LocalDate today
            );
}
