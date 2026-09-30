package com.wpinball.analytics.anticheat.rule;

import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.result.AnalysisResult;
import com.wpinball.analytics.anticheat.result.Verdict;
import org.springframework.stereotype.Component;

@Component
public class ScoreThresholdRule implements AntiCheatRule {

    private static final long SUSPECT_THRESHOLD = 1_000_000_000L;

    @Override
    public AnalysisResult evaluate(ScoreSubmittedEvent event) {
        if (event.score() > SUSPECT_THRESHOLD) {
            return new AnalysisResult(
                    Verdict.FLAGGED,
                    "ScoreThreshold",
                    "Score %d exceeds threshold of %d".formatted(event.score(), SUSPECT_THRESHOLD)
            );
        }
        return new AnalysisResult(Verdict.LEGIT, "ScoreThreshold", "Score within limits");
    }
}
