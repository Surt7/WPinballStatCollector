package com.wpinball.analytics.command;


import java.time.Instant;

public record RecordScoreCommand(
        String playerName,
        String machineName,
        long score,
        Instant playedAt
) {
}