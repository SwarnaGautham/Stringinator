package com.comcast.stringinator.model;


import lombok.Getter;

@Getter
public class ComputationResult {
    private final String input;
    private final Integer length;

    public ComputationResult(String input, Integer length) {
        this.input = input;
        this.length = length;
    }

}
