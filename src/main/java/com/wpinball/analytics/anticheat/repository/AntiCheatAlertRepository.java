package com.wpinball.analytics.anticheat.repository;

import com.wpinball.analytics.anticheat.model.AntiCheatAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AntiCheatAlertRepository extends JpaRepository<AntiCheatAlert, UUID> {
    
}