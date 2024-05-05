package com.tps.services.impl;

import com.tps.pojo.Pointgroup;
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
    public void addOrUpdate(Pointgroup pointgroup) {
        this.pointGroupRepository.addOrUpdate(pointgroup);
    }

    @Override
    public void delete(Pointgroup pointgroup) {
        this.pointGroupRepository.delete(pointgroup);
    }

    @Override
    public Pointgroup getPointgroup(int id) {
        return this.pointGroupRepository.getPointgroup(id);
    }

    @Override
    public List<Pointgroup> getAllPointGroups() {
        return this.pointGroupRepository.getAllPointGroups();
    }
}
