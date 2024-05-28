package com.comcast.stringinator.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class StatisticsResult {
    private final Map<String, Integer> inputs;
    public StatisticsResult(Map<String, Integer> inputs) {
        this.inputs = inputs;
    }

}
