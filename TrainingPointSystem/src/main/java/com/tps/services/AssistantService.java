package com.tps.services;

import com.tps.pojo.Assistant;

import java.util.List;
import java.util.Map;

public interface AssistantService {
    void addAssistant(Assistant assistant);
    void updateAssistant(Assistant assistant);
    Assistant getAssistantById(int id);
    List<Object[]> getUserAssistants(Map<String, String> params);
}
