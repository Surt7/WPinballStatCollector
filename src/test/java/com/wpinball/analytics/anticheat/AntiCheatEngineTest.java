package com.wpinball.analytics.anticheat;

import com.wpinball.analytics.anticheat.engine.AntiCheatEngine;
import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.result.AnalysisResult;
import com.wpinball.analytics.anticheat.result.Verdict;
import com.wpinball.analytics.anticheat.rule.ScoreThresholdRule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AntiCheatEngineTest {

    private final ScoreThresholdRule thresholdRule = new ScoreThresholdRule();
    private final AntiCheatEngine engine = new AntiCheatEngine(List.of(thresholdRule));


    @Test
    void should_return_legit_when_score_is_under_threshold() {
        ScoreSubmittedEvent event = new ScoreSubmittedEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                1,
                "user-123",
                50_000_000L,
                Instant.now()
        );
        List<AnalysisResult> results = engine.analyze(event);

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().verdict()).isEqualTo(Verdict.LEGIT);


    }


    @Test
    void should_return_flagged_when_score_exceeds_threshold() {
        // ARRANGE
        ScoreSubmittedEvent event = new ScoreSubmittedEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                1,
                "cheater-999",
                2_000_000_000L,
                Instant.now()
        );
        // ACT
        List<AnalysisResult> results = engine.analyze(event);
        // ASSERT
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().verdict()).isEqualTo(Verdict.FLAGGED);
        assertThat(results.getFirst().reason()).contains("exceeds threshold");
    }


    void should_return_suspect_when_score_is_suspiciously_round() {

        ScoreSubmittedEvent event = new ScoreSubmittedEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                1,
                "player-trainer-user",
                50_000_000L,
                Instant.now()
        );

        List<AnalysisResult> results = engine.analyze(event);


        assertThat(results).hasSize(2);


        assertThat(results.get(0).verdict()).isEqualTo(Verdict.LEGIT);


        assertThat(results.get(1).verdict()).isEqualTo(Verdict.SUSPECT);
        assertThat(results.get(1).reason()).contains("suspiciously round");
    }

}
