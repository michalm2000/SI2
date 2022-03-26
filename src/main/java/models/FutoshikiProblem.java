package models;

import org.javatuples.Pair;

import java.util.Map;

public class FutoshikiProblem {
    private int[] numberArray;
    private int[] domain;
    private int dim;
    private Map<Pair<Integer, Integer>, Character> constraintsMap;

    public FutoshikiProblem(Pair<int[], Map<Pair<Integer, Integer>, Character>> data, int[] domain){
        this.numberArray = data.getValue0();
        this.constraintsMap = data.getValue1();
        this.domain = domain;
    }



}
