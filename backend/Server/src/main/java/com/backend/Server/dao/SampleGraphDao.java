package com.backend.Server.dao;

import com.backend.Server.model.Direction;
import com.backend.Server.model.Poi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("GraphData")
public class SampleGraphDao implements GraphDao{
    @Override
    public List<List<Integer>> constructGraph(Direction direction) {
        return null;
    }

    @Override
    public List<List<Integer>> constructWeight(Direction direction) {
        return null;
    }

    @Override
    public int[] metaGraph() {
        return new int[0];
    }

    @Override
    public List<Poi> match(List<Integer> path) {
        return null;
    }
}
