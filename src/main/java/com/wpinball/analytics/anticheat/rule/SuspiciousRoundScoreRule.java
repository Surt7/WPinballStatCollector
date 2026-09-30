package com.wpinball.analytics.anticheat.rule;

import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.result.AnalysisResult;
import com.wpinball.analytics.anticheat.result.Verdict;
import org.springframework.stereotype.Component;

@Component
public class SuspiciousRoundScoreRule implements AntiCheatRule {
    private static final long MIN_SCORE_CHECK = 1_000_000L;
    private static final long ROUND_MODULO = 100_000L;

    @Override
    public AnalysisResult evaluate(ScoreSubmittedEvent event) {
        long score = event.score();

        if (score >= MIN_SCORE_CHECK && score % ROUND_MODULO == 0) {
            return new AnalysisResult(
                    Verdict.SUSPECT,
                    "SuspiciousRoundScore",
                    "Score %d is suspiciously round (multiple of %d)".formatted(score, ROUND_MODULO)
            );
        }

        return new AnalysisResult(Verdict.LEGIT, "SuspiciousRoundScore", "Score pattern is natural");
    }
}
