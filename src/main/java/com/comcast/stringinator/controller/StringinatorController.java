package com.comcast.stringinator.controller;

import com.comcast.stringinator.model.OutputDTO;
import com.comcast.stringinator.model.StatisticsResult;
import com.comcast.stringinator.model.InputMessage;
import com.comcast.stringinator.model.ComputationResult;

import com.comcast.stringinator.service.StringinatorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
public class StringinatorController {

    @Autowired
    private StringinatorServiceImpl stringinatorService;

    @GetMapping("/")
	public String index() {
		return "<pre>\n" +
		"Welcome to the Stringinator 3000 for all of your string manipulation needs.\n" +
		"GET / - You're already here! \n" +
		"POST /stringinate - Get all of the info you've ever wanted about a string. Takes JSON of the following form: {\"input\":\"your-string-goes-here\"}\n" +
		"GET /stats - Get statistics about all strings the server has seen, including the longest and most popular strings.\n" +
		"</pre>";
	}

    @GetMapping(path = "/stringinate", produces = "application/json")
    public OutputDTO findMostFrequentCharacter(@Valid @RequestParam(name = "input", required = true) String input) {
        log.info("Input received.....{}",input);
        return stringinatorService.findMostFrequentCharFromString(input);
    }

	@PostMapping(path = "/stringinate", consumes = "application/json", produces = "application/json")
    public ComputationResult processInputMessage(@Valid @RequestBody InputMessage input) {
        log.info("Input Message Received.......{}",input);
        return stringinatorService.computingInputMessage(input);
    }

    @GetMapping(path = "/stats", produces = "application/json")
    public Map<String,String> getStatisticsData() {
        Map<String,String> output=new HashMap<>();
        StatisticsResult result = stringinatorService.statistics();
        log.info("Predicting the most popular word.......");
        output.put("most_popular",result.getInputs().entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey());
        log.info("Predicting the longest input word.......");
        output.put("longest_input_received",result.getInputs().entrySet().stream().map(Map.Entry::getKey).max(Comparator.comparing(String::length)).get());
        return output;
    }


    @GetMapping(path = "/anagram", produces = "application/json")
    public String anagramPrediction(@RequestParam(name = "input1", required = true) String input1,@RequestParam(name = "input2", required = true) String input2) {
         return stringinatorService.getAnagramPrediction(input1,input2) ? "Anagram found":"Anagram not found";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> ValidationExceptionHandler(
            MethodArgumentNotValidException ex) {
        log.error("validation failed");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
