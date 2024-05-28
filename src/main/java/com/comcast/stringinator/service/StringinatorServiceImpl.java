package com.comcast.stringinator.service;


import com.comcast.stringinator.model.OutputDTO;
import com.comcast.stringinator.model.StatisticsResult;
import com.comcast.stringinator.model.InputMessage;
import com.comcast.stringinator.model.ComputationResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class StringinatorServiceImpl implements StringinatorService {

    private Map<String, Integer> seenStrings = new HashMap<>();

    @Override
    public ComputationResult computingInputMessage(InputMessage input) {
        log.info("Storing data for statistics");
        if(input.getInput().trim().contains(" ")) {
            List<String> inputList= Arrays.stream(input.getInput().split(" ")).collect(Collectors.toList());
            for(String inputData:inputList)
                seenStrings.compute(inputData, (k, v) -> (v == null) ? Integer.valueOf(1) : v + 1);
        }
        else {
            seenStrings.compute(input.getInput(), (k, v) -> (v == null) ? Integer.valueOf(1) : v + 1);
        }
        log.info("Input message processed");
        ComputationResult result = new ComputationResult(input.getInput(), Integer.valueOf(input.getInput().length()));
        return result;
    }

    @Override
    public OutputDTO findMostFrequentCharFromString(String input) {
        log.info("Validation Completed....Finding the frequency");
        seenStrings.compute(input, (k, v) -> (v == null) ? Integer.valueOf(1) : v + 1);
        Map.Entry<Character, Long> entry=input.chars().mapToObj(i-> (char)i).collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();
        log.info("Most frequent character found : {}  with length : {} ",entry.getKey().toString(),entry.getValue().intValue());
        return new OutputDTO(entry.getKey().toString(),entry.getValue().intValue());
    }

    @Override
    public StatisticsResult statistics() {
        log.info("Statics map prepared.....{}",seenStrings);
        return new StatisticsResult(seenStrings);
    }

    @Override
    public Boolean getAnagramPrediction(String input1, String input2) {
        log.info("Anagram prediction started for {},{}",input1,input2);
        HashMap<Character, Integer> map = new HashMap<>();
        if (input1.length() != input2.length()) {
            return false;
        }
        for (int i = 0; i < input1.length(); i++) {
            if (map.containsKey(input1.charAt(i))) {
                map.put(input1.charAt(i),
                        map.get(input1.charAt(i)) + 1);
            }
            else {
                map.put(input1.charAt(i), 1);
            }
        }
        for (int i = 0; i < input2.length(); i++) {
            if (map.containsKey(input2.charAt(i))) {
                map.put(input2.charAt(i),
                        map.get(input2.charAt(i)) - 1);
            }
            else {
                return false;
            }
        }
        Set<Character> keys = map.keySet();
        for (Character key : keys) {
            if (map.get(key) != 0) {
                return false;
            }
        }
        return true;
    }
}

