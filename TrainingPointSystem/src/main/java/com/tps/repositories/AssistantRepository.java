package com.tps.repositories;

import com.tps.pojo.Assistant;

import java.util.List;
import java.util.Map;

public interface AssistantRepository {
    void addAssistant(Assistant assistant);
    void updateAssistant(Assistant assistant);
    void deleteAssistant(Assistant assistant);
    List<Assistant> getAllAssistants(Map<String, String> params);
    Assistant getAssistantById(String id);
    List<Object[]> getUserAssistants(Map<String, String> params);
}
