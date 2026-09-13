package com.wpinball.analytics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "game_scores")
@Getter
@Setter
@NoArgsConstructor
public class GameScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String gameId;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private String machineName;

    @Column(nullable = false)
    private long score;

    @Column(nullable = false)
    private Instant playedAt;

    @Column(nullable = false)
    private Instant collectedAt;

    public GameScore(String gameId, String playerName, String machineName, long score, Instant playedAt) {
        this.gameId = gameId;
        this.playerName = playerName;
        this.machineName = machineName;
        this.score = score;
        this.playedAt = playedAt;
        this.collectedAt = Instant.now();
    }
}
