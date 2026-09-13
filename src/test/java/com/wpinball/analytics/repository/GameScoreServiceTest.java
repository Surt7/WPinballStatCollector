package com.wpinball.analytics.repository;

import com.wpinball.analytics.command.RecordScoreCommand;
import com.wpinball.analytics.command.RecordScoreHandler;
import com.wpinball.analytics.model.GameScore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameScoreServiceTest {

    @Mock
    private GameScoreRepository gameScoreRepository;

    @InjectMocks
    private RecordScoreHandler handler;

    @Test
    void should_record_and_save_successfully() {
        RecordScoreCommand command = new RecordScoreCommand(
                "TOM",
                "Medieval Madness",
                150_000_000L,
                Instant.now()
        );

        when(gameScoreRepository.save(any(GameScore.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        GameScore result = handler.handle(command);

        // ASSERT
        assertThat(result.getScore()).isEqualTo(150_000_000L);
        assertThat(result.getPlayerName()).isEqualTo("TOM");
        verify(gameScoreRepository, times(1)).save(any(GameScore.class));
    }
}
