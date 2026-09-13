package com.wpinball.analytics.command;


import com.wpinball.analytics.model.GameScore;
import com.wpinball.analytics.repository.GameScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordScoreHandler {
    private final GameScoreRepository repository;


    @Transactional
    public GameScore handle(RecordScoreCommand command) {
        // 1. On crée l'entité avec son constructeur
        GameScore score = new GameScore(
                java.util.UUID.randomUUID().toString(),
                command.playerName(),
                command.machineName(),
                command.score(),
                command.playedAt() != null ? command.playedAt() : java.time.Instant.now()
        );
        // 2. On sauvegarde en base et on retourne l'entité enregistrée
        return repository.save(score);
    }

}
