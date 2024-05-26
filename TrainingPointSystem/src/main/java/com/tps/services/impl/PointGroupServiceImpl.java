package com.tps.services.impl;

import com.tps.pojo.PointGroup;
import com.tps.repositories.PointGroupRepository;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointGroupServiceImpl implements PointGroupService {

    @Autowired
    private PointGroupRepository pointGroupRepository;

    @Override
    public void addOrUpdate(PointGroup pointgroup) {
        this.pointGroupRepository.addOrUpdate(pointgroup);
    }

    @Override
    public void delete(PointGroup pointgroup) {
        this.pointGroupRepository.delete(pointgroup);
    }

    @Override
    public PointGroup getPointGroup(int id) {
        return this.pointGroupRepository.getPointGroup(id);
    }

    @Override
    public List<PointGroup> getAllPointGroups() {
        return this.pointGroupRepository.getAllPointGroups();
    }
}
