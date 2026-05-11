package com.vijay.service.impl;

import com.vijay.mapper.SubscriptionPlanMapper;
import com.vijay.modal.SubscriptionPlan;
import com.vijay.modal.User;
import com.vijay.payload.dto.SubscriptionPlanDTO;
import com.vijay.payload.dto.UserDto;
import com.vijay.repository.SubscriptionPlanRepository;
import com.vijay.service.SubscriptionPlanService;
import com.vijay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SubscriptionPlanImpl implements SubscriptionPlanService {


    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserService userService;
    private final SubscriptionPlanMapper subscriptionPlanMapper;


    @Override
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception {

        if (subscriptionPlanRepository.existsByPlanCode(planDTO.getPlanCode())) {
            throw new Exception("plan code already exists");
        }

        SubscriptionPlan plan = SubscriptionPlanMapper.toEntity(planDTO);

        // set createdBy BEFORE saving
        UserDto currentUser = userService.getCurrentUser();
        plan.setCreatedBy(currentUser.getEmail());
        plan.setUpdatedBy(currentUser.getEmail());

        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(plan);
        return SubscriptionPlanMapper.toDTO(savedPlan);
    }

    @Override
    public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws Exception {
        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(planId).orElseThrow(
                () -> new Exception("Subscription Plan not found")
        );

        SubscriptionPlanMapper.updateEntityFromDTO(planDTO, existingPlan);
        UserDto currentUser = userService.getCurrentUser();
        existingPlan.setUpdatedBy(currentUser.getEmail());
        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);

        return SubscriptionPlanMapper.toDTO(updatedPlan);
    }

    @Override
    public void deleteSubscriptionPlan(Long planId) throws Exception {
        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(planId).orElseThrow(
                () -> new Exception("Plan not found")
        );
        subscriptionPlanRepository.delete(existingPlan);
    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {
        return subscriptionPlanRepository.findAll()
                .stream()
                .map(SubscriptionPlanMapper::toDTO)
                .toList();
    }

}
