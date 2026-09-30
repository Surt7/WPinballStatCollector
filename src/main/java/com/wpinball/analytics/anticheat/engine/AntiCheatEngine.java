package com.wpinball.analytics.anticheat.engine;

import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.result.AnalysisResult;
import com.wpinball.analytics.anticheat.result.Verdict;
import com.wpinball.analytics.anticheat.rule.AntiCheatRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AntiCheatEngine {

    private final List<AntiCheatRule> rules;

    public List<AnalysisResult> analyze(ScoreSubmittedEvent event) {
        log.info("Analyzing score {} for user {}", event.score(), event.userId());

        List<AnalysisResult> results = rules.stream()
                .map(rule -> rule.evaluate(event))
                .toList();

        boolean hasFlagged = results.stream()
                .anyMatch(r -> r.verdict() == Verdict.FLAGGED);

        if (hasFlagged) {
            log.warn("FLAGGED score {} from user {}", event.score(), event.userId());
        }

        return results;
    }
}