package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinaryProblem {
    private int[] binaryArray;
    private int[] domain;

    public BinaryProblem(int[] binaryArray, int[] domain){
        this.binaryArray = binaryArray;
        this.domain = domain;
    }
    public List<Possibility> generateResults() {
        List<Possibility> possibilitiesToCheck = new LinkedList<>();
        List<Possibility> results = new ArrayList<>();
        int[] copyArray = binaryArray.clone();
        Possibility pos = new Possibility(copyArray);
        possibilitiesToCheck.add(pos);
        while (!possibilitiesToCheck.isEmpty()){
            Possibility p = possibilitiesToCheck.get(0);
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < Math.sqrt(binaryArray.length); i++){
            for(int j = 0; j < Math.sqrt(binaryArray.length); j++){
                stringBuilder.append(binaryArray[i + j]).append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

}