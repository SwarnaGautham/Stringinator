package com.comcast.stringinator.service;

import com.comcast.stringinator.model.OutputDTO;
import com.comcast.stringinator.model.StatisticsResult;
import com.comcast.stringinator.model.InputMessage;
import com.comcast.stringinator.model.ComputationResult;

import java.util.Map;

public interface StringinatorService {
    ComputationResult computingInputMessage(InputMessage input);

    OutputDTO findMostFrequentCharFromString(String input);

    StatisticsResult statistics();
    Boolean getAnagramPrediction(String input1, String input2);
}