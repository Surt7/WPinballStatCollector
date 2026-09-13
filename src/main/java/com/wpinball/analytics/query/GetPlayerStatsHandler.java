package com.wpinball.analytics.query;

import com.wpinball.analytics.dto.PlayerStatsDto;
import com.wpinball.analytics.exception.MachineNotFoundException;
import com.wpinball.analytics.repository.GameScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPlayerStatsHandler {

    private final GameScoreRepository repository;


    public PlayerStatsDto handle(GetPlayerStatsQuery query) {
        PlayerStatsDto stats = repository.getPlayerStats(query.playerName())
                .orElseThrow(() -> new MachineNotFoundException("Joueur introuvable : " + query.playerName()));

        String favoriteMachine = repository.findFavoriteMachineByPlayerName(query.playerName())
                .orElse("Aucune");

        
        return new PlayerStatsDto(
                stats.playerName(),
                stats.totalGames(),
                stats.highestScore(),
                stats.averageScore(),
                favoriteMachine
        );
    }

}
