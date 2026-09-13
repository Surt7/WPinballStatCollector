package com.wpinball.analytics.controller;

import com.wpinball.analytics.command.RecordScoreCommand;
import com.wpinball.analytics.command.RecordScoreHandler;
import com.wpinball.analytics.query.GetDistinctMachineHandler;
import com.wpinball.analytics.dto.GameFinishedEvent;
import com.wpinball.analytics.dto.MachineStatsDto;
import com.wpinball.analytics.dto.PlayerStatsDto;
import com.wpinball.analytics.model.GameScore;
import com.wpinball.analytics.query.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScoreController {

    private final RecordScoreHandler recordScoreHandler;
    private final GetLeaderboardHandler leaderboardHandler;
    private final GetMachineStatsHandler machineStatsHandler;
    private final GetPlayerStatsHandler playerStatsHandler;
    private final GetDistinctMachineHandler distinctMachineHandler;

    @PostMapping("/events/score")
    public ResponseEntity<GameScore> recordScore(@Valid @RequestBody GameFinishedEvent event) {
        RecordScoreCommand command = new RecordScoreCommand(
                event.playerName(),
                event.machineName(),
                event.score(),
                event.playedAt()
        );
        GameScore saved = recordScoreHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/leaderboard/{machineName}")
    public ResponseEntity<List<GameScore>> getLeaderboard(@PathVariable String machineName) {
        GetLeaderboardQuery query = new GetLeaderboardQuery(machineName);
        List<GameScore> leaderboard = leaderboardHandler.handle(query);
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/stats/machine/{machineName}")
    public ResponseEntity<MachineStatsDto> getMachineStats(@PathVariable String machineName) {
        GetMachineStatsQuery query = new GetMachineStatsQuery(machineName);
        MachineStatsDto stats = machineStatsHandler.handle(query);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/player/{playerName}")
    public ResponseEntity<PlayerStatsDto> getPlayerStats(@PathVariable String playerName) {
        GetPlayerStatsQuery query = new GetPlayerStatsQuery(playerName);
        PlayerStatsDto stats = playerStatsHandler.handle(query);
        return ResponseEntity.ok(stats);
    }


    @GetMapping
    public ResponseEntity<List<String>> getDistinctMachines() {
        List<String> machines = distinctMachineHandler.handle(new GetDistinctMachineQuery());

        return ResponseEntity.ok(machines);
    }
}