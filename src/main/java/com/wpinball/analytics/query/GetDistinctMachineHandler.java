package com.wpinball.analytics.query;


import com.wpinball.analytics.repository.GameScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDistinctMachineHandler {
    private final GameScoreRepository repository;


    @Transactional(readOnly = true)
    public List<String> handle(GetDistinctMachineQuery query) {
        return repository.findDistinctMachineNames();
    }
}
