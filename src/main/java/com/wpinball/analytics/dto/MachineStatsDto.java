package com.wpinball.analytics.dto;

public record MachineStatsDto(
        String machineName,
        Double averageScore,
        Long highestScore,
        Long totalGames
) {}
