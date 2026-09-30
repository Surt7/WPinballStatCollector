package com.wpinball.analytics.anticheat.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.Instant;
import java.util.UUID;


@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public record ScoreSubmittedEvent(
        UUID scoreEntryId,
        UUID sessionId,
        UUID machineId,
        UUID modeId,
        int slotNumber,
        String userId,
        long score,
        Instant achievedAtUtc
) {
}
