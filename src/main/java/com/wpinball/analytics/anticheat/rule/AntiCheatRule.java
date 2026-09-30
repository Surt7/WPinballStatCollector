package com.wpinball.analytics.anticheat.rule;

import com.wpinball.analytics.anticheat.model.ScoreSubmittedEvent;
import com.wpinball.analytics.anticheat.result.AnalysisResult;

public interface AntiCheatRule {
    AnalysisResult evaluate(ScoreSubmittedEvent event);
}