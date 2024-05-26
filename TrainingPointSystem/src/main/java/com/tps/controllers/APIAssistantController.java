package com.tps.controllers;

import com.tps.services.AssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("/api/assistants")
public class APIAssistantController {

    @Autowired
    AssistantService assistantService;


    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAssistants(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> userIds = request.get("userIds");
        this.assistantService.deleteAsistantsByIds(userIds);
    }
}
