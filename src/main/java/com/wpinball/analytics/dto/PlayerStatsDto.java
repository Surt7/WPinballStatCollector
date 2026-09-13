package com.wpinball.analytics.dto;

public record PlayerStatsDto(
        String playerName,
        Long totalGames,
        Long highestScore,
        Double averageScore,
        String favoriteMachine
) {
}
