package com.tps.services;

import com.tps.pojo.Pointgroup;

import java.util.List;

public interface PointGroupService {
    void addOrUpdate(Pointgroup pointgroup);
    Pointgroup getPointgroup(int id);
    List<Pointgroup> getAllPointGroups();
}
