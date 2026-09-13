package com.wpinball.analytics.query;

import com.wpinball.analytics.dto.MachineStatsDto;
import com.wpinball.analytics.exception.MachineNotFoundException;
import com.wpinball.analytics.model.GameScore;
import com.wpinball.analytics.repository.GameScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMachineStatsHandler {
    private final GameScoreRepository repository;

    @Transactional(readOnly = true)
    public MachineStatsDto handle(GetMachineStatsQuery query) {
        return repository.getMachinesStats(query.machineName())
                .orElseThrow(() -> new MachineNotFoundException("Machine introuvable : " + query.machineName()));
    }
}
