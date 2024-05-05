package com.tps.repositories;

import com.tps.pojo.Class;

import java.util.List;
import java.util.Map;

public interface ClassRepository {
    void addClass(Class c);
    void updateClass(Class c);
    void deleteClass(Class c);
    Class getClassById(int id);
    List<Class> getAllClass(Map<String, String> params);
}
