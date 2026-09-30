package com.wpinball.analytics.anticheat.result;

public record AnalysisResult(
        Verdict verdict,
        String ruleName,
        String reason
) {
}
