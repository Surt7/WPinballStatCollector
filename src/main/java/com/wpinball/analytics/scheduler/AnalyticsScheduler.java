package com.wpinball.analytics.scheduler;


import com.wpinball.analytics.repository.GameScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalyticsScheduler {
    private final GameScoreRepository gameScoreRepository;

    @Scheduled(fixedRate = 30000)
    public void generateAnalyticsSnapshot() {
        long totalGames = gameScoreRepository.count();

        log.info("[BILAN ANALYTICS] Total de parties enregistrées en base: {}", totalGames);
    }
}
