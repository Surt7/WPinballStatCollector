package com.wpinball.analytics.anticheat;

import com.wpinball.analytics.anticheat.engine.AntiCheatEngine;
import com.wpinball.analytics.anticheat.model.AntiCheatAlert;
import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.repository.AntiCheatAlertRepository;
import com.wpinball.analytics.anticheat.result.AnalysisResult;
import com.wpinball.analytics.anticheat.result.Verdict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AntiCheatConsumer {


    private final AntiCheatEngine engine;
    private final AntiCheatAlertRepository alertRepository;

    @KafkaListener(topics = "game-sessions", groupId = "wpinball-anticheat-group")
    public void onScoreSubmitted(ScoreSubmittedEvent event) {
        log.info("Received score event for entry {}", event.scoreEntryId());

        List<AnalysisResult> results = engine.analyze(event);


        for (AnalysisResult result : results) {
            if (result.verdict() != Verdict.LEGIT) {
                log.warn("Rule [{}]: {} - {}", result.ruleName(), result.verdict(), result.reason());

                AntiCheatAlert alert = new AntiCheatAlert(
                        event.scoreEntryId(),
                        event.sessionId(),
                        event.machineId(),
                        event.userId(),
                        event.score(),
                        result.verdict(),
                        result.ruleName(),
                        result.reason()

                );
                alertRepository.save(alert);

            }
        }

    }


}
