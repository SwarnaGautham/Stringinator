package com.comcast.stringinator.controller;

import com.comcast.stringinator.model.OutputDTO;
import com.comcast.stringinator.service.StringinatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


class StringinatorControllerTest {

    @InjectMocks
    private StringinatorController stringinatorController;

    @Mock
    private StringinatorServiceImpl stringinatorService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findMostFrequentCharacter() {
        String input="swarnaaaa";
        OutputDTO output=new OutputDTO("a",3);
        when(stringinatorService.findMostFrequentCharFromString(ArgumentMatchers.anyString())).thenReturn(output);
        OutputDTO result=stringinatorController.findMostFrequentCharacter(input);
        assertNotNull(result);
        assertEquals(output.getMost_frequent_character(),result.getMost_frequent_character());
        assertEquals(output.getNo_of_occurrences_of_most_frequent_character(),result.getNo_of_occurrences_of_most_frequent_character());
    }
}