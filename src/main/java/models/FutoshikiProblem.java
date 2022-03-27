package models;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    public List<FutoshikiPossibility> generateResults() {
        List<FutoshikiPossibility> possibilitiesToCheck = new LinkedList<>();
        List<FutoshikiPossibility> results = new ArrayList<>();
        int[] copyArray = numberArray.clone();
        FutoshikiPossibility pos = new FutoshikiPossibility(copyArray, constraintsMap, domain);
        possibilitiesToCheck.add(pos);
        while (!possibilitiesToCheck.isEmpty()){
            FutoshikiPossibility p = possibilitiesToCheck.get(0);
            if(p.isComplete()){
                results.add(p);
            }
            else {
                possibilitiesToCheck.addAll(p.spawnChildren());
            }
            possibilitiesToCheck.remove(0);
        }
        return results;
    }






}
