package com.comcast.stringinator.model;


import lombok.Getter;

@Getter
public class OutputDTO {

    private final String most_frequent_character;
    private final Integer no_of_occurrences_of_most_frequent_character;

    public OutputDTO(String input, Integer length) {
        this.most_frequent_character = input;
        this.no_of_occurrences_of_most_frequent_character = length;
    }
}
