package com.tps.services;

import com.tps.pojo.PointGroup;

import java.util.List;

public interface PointGroupService {
    void addOrUpdate(PointGroup pointgroup);
    void delete(PointGroup pointgroup);
    PointGroup getPointGroup(int id);
    List<PointGroup> getAllPointGroups();
}
