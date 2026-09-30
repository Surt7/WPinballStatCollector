package com.wpinball.analytics.anticheat.model;

import com.wpinball.analytics.anticheat.result.Verdict;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "anticheat_alerts")
@Getter
@NoArgsConstructor
public class AntiCheatAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID scoreEntryId;

    @Column(nullable = false)
    private UUID sessionId;

    @Column(nullable = false)
    private UUID machineId;

    private String userId;

    @Column(nullable = false)
    private long score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Verdict verdict;

    @Column(nullable = false)
    private String ruleName;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private Instant detectedAt;

    public AntiCheatAlert(
            UUID scoreEntryId,
            UUID sessionId,
            UUID machineId,
            String userId,
            long score,
            Verdict verdict,
            String ruleName,
            String reason
    ) {
        this.scoreEntryId = scoreEntryId;
        this.sessionId = sessionId;
        this.machineId = machineId;
        this.userId = userId;
        this.score = score;
        this.verdict = verdict;
        this.ruleName = ruleName;
        this.reason = reason;
        this.detectedAt = Instant.now();
    }
}