package com.tps.repositories;

import com.tps.pojo.Pointgroup;

import java.util.List;

public interface PointGroupRepository {
    void addOrUpdate(Pointgroup pointgroup);
    Pointgroup getPointgroup(int id);
    List<Pointgroup> getAllPointGroups();
}
