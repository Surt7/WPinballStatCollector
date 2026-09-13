package com.wpinball.analytics.query;

import com.wpinball.analytics.exception.MachineNotFoundException;
import com.wpinball.analytics.model.GameScore;
import com.wpinball.analytics.repository.GameScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLeaderboardHandler {
    private final GameScoreRepository repository;


    @Transactional(readOnly = true)
    public List<GameScore> handle(GetLeaderboardQuery query) {
        List<GameScore> scores = repository.findTop10ByMachineNameOrderByScoreDesc(query.machineName());
        if (scores.isEmpty()) {
            throw new MachineNotFoundException("Machine introuvable : " + query.machineName());
        }
        return scores;
    }
}
