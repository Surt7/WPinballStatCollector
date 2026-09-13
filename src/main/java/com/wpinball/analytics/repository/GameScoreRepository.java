package com.wpinball.analytics.repository;

import com.wpinball.analytics.dto.MachineStatsDto;
import com.wpinball.analytics.dto.PlayerStatsDto;
import com.wpinball.analytics.model.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {

    List<GameScore> findTop10ByMachineNameOrderByScoreDesc(String machineName);

    List<GameScore> findByPlayerNameOrderByPlayedAtDesc(String playerName);



    @Query("""
            SELECT new com.wpinball.analytics.dto.MachineStatsDto(
            s.machineName,
            AVG(s.score),
            MAX(s.score),
            COUNT(s)
            )
            FROM GameScore s
            WHERE s.machineName = :machineName
            GROUP BY s.machineName
            
            """)
    Optional<MachineStatsDto> getMachinesStats(@Param("machineName") String machineName);


    @Query("""
            SELECT new com.wpinball.analytics.dto.PlayerStatsDto(
            s.playerName,
            COUNT(s),
            MAX(s.score),
            AVG(s.score),
            ''
            )
            FROM GameScore s
            WHERE s.playerName = :playerName
            GROUP BY s.playerName
            """)
    Optional<PlayerStatsDto> getPlayerStats(@Param("playerName") String playerName);

    @Query("""
            SELECT s.machineName
            FROM GameScore s
            WHERE s.playerName = :playerName
            GROUP BY s.machineName
            ORDER BY COUNT(s) DESC
            LIMIT 1
            """)
    Optional<String> findFavoriteMachineByPlayerName(@Param("playerName") String playerName);

    @Query("""
            SELECT DISTINCT s.machineName
            FROM GameScore s
            """)
    List<String> findDistinctMachineNames();
}
