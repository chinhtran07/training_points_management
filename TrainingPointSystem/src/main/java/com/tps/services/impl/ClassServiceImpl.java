package com.tps.services.impl;

import com.tps.pojo.Class;
import com.tps.repositories.ClassRepository;
import com.tps.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Override
    public void addClass(Class c) {
        this.classRepository.addClass(c);
    }

    @Override
    public void updateClass(Class c) {
        this.classRepository.updateClass(c);
    }

    @Override
    public void deleteClass(Class c) {
        this.classRepository.deleteClass(c);
    }

    @Override
    public Class getClassById(int id) {
        return this.classRepository.getClassById(id);
    }

    @Override
    public List<Class> getAllClass(Map<String, String> params) {
        return this.classRepository.getAllClass(params);
    }
}
