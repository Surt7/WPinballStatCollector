package com.wpinball.analytics.consumer;


import com.wpinball.analytics.dto.GameFinishedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.wpinball.analytics.command.RecordScoreCommand;
import com.wpinball.analytics.command.RecordScoreHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoreEventConsumer {
    private final RecordScoreHandler recordScoreHandler;

    @KafkaListener(topics = "pinball.game-events", groupId = "wpinball-analytics-group")
    public void consumeGameFinishedEvent(GameFinishedEvent event) {
        log.info("Événement Kafka reçu : Joueur={}, Machine={}, Score={}",
                event.playerName(), event.machineName(), event.score());
        RecordScoreCommand command = new RecordScoreCommand(
                event.playerName(),
                event.machineName(),
                event.score(),
                event.playedAt()
        );
        recordScoreHandler.handle(command);
    }
}
