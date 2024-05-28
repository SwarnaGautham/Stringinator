package com.comcast.stringinator.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InputMessage {
    @NotBlank(message = "Input is mandatory")
    private String input;
}
