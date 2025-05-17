package com.moodcheck.stats_service.repository;

import com.moodcheck.stats_service.model.MoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodRepository extends JpaRepository<MoodEntity, Long> {
    List<MoodEntity> findByUserId(String userId);
}

