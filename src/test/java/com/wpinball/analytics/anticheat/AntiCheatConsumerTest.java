package com.wpinball.analytics.anticheat;

import com.wpinball.analytics.anticheat.engine.AntiCheatEngine;
import com.wpinball.analytics.anticheat.model.AntiCheatAlert;
import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.repository.AntiCheatAlertRepository;
import com.wpinball.analytics.anticheat.result.AnalysisResult;
import com.wpinball.analytics.anticheat.result.Verdict;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AntiCheatConsumerTest {

    @Mock
    private AntiCheatEngine engine;

    @Mock
    private AntiCheatAlertRepository alertRepository;

    @InjectMocks
    private AntiCheatConsumer consumer;

    @Test
    void should_not_save_alert_when_all_rules_are_legit() {
        // ARRANGE
        ScoreSubmittedEvent event = createEvent(100_000L);
        when(engine.analyze(event)).thenReturn(List.of(
                new AnalysisResult(Verdict.LEGIT, "ScoreThreshold", "OK")
        ));

        // ACT
        consumer.onScoreSubmitted(event);

        // ASSERT - Zéro sauvegarde en base si le score est legit
        verify(alertRepository, never()).save(any(AntiCheatAlert.class));
    }

    @Test
    void should_save_alert_when_verdict_is_flagged_or_suspect() {
        // ARRANGE
        ScoreSubmittedEvent event = createEvent(2_000_000_000L);
        when(engine.analyze(event)).thenReturn(List.of(
                new AnalysisResult(Verdict.FLAGGED, "ScoreThreshold", "Score exceeds limit")
        ));

        // ACT
        consumer.onScoreSubmitted(event);

        // ASSERT - Doit appeler save 1 fois
        verify(alertRepository, times(1)).save(any(AntiCheatAlert.class));
    }

    private ScoreSubmittedEvent createEvent(long score) {
        return new ScoreSubmittedEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                1,
                "user-test",
                score,
                Instant.now()
        );
    }
}