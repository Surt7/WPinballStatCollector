package com.wpinball.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

public record GameFinishedEvent(
    @NotBlank(message = "L'ID de la partie est obligatoire")
    String gameId,

    @NotBlank(message = "Le nom du joueur est obligatoire")
    String playerName,

    @NotBlank(message = "Le nom de la machine est obligatoire")
    String machineName,

    @Positive(message = "Le score doit être supérieur à 0")
    long score,

    @NotNull(message = "La date de jeu est obligatoire")
    Instant playedAt
) {}
