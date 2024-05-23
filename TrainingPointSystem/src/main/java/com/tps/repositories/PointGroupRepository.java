package com.tps.repositories;

import com.tps.pojo.PointGroup;

import java.util.List;

public interface PointGroupRepository {
    void addOrUpdate(PointGroup pointgroup);
    void delete(PointGroup pointgroup);
    PointGroup getPointGroup(int id);
    List<PointGroup> getAllPointGroups();
}
