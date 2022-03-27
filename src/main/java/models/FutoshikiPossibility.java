package models;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class FutoshikiPossibility {
    private int[] numberArray;
    private int dim;
    private boolean valid;
    private boolean complete;
    private Map<Pair<Integer, Integer>, Character> constraintsMap;
    private int[] domain;

    public FutoshikiPossibility(int[] numberArray, Map<Pair<Integer, Integer>, Character> constraintsMap, int[] domain) {
        this.numberArray = numberArray;
        this.constraintsMap = constraintsMap;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = true;
        this.domain = domain;
    }

    public FutoshikiPossibility(int[] numArray, int changedIndex,  Map<Pair<Integer, Integer>, Character> constraintsMap, int[] domain){
        this.numberArray = numArray;
        this.constraintsMap = constraintsMap;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = checkRow(changedIndex/ dim) && checkColumn( changedIndex % dim) && checkConstraints();
        this.domain = domain;
    }

    public boolean checkRow(int rowNumber){
        int[] row = Arrays.stream(Arrays.copyOfRange(numberArray, rowNumber*dim, rowNumber*dim +dim)).filter(c-> c != -1).toArray();
        return Arrays.stream(row).distinct().count() == Arrays.stream(row).count() || Arrays.stream(row).distinct().count() <= 1;
    }

    public boolean checkColumn (int colNumber){
        int[] column = new int[dim];
        for (int i = 0; i < dim; i++){
            column[i] = numberArray[i*dim + colNumber];
        }

        column = Arrays.stream(column).filter(c -> c != -1).toArray();

        return Arrays.stream(column).distinct().count() == Arrays.stream(column).count() || Arrays.stream(column).distinct().count() <= 1;
    }

    public boolean checkConstraints(){
        for(var key: constraintsMap.keySet()){
            if (!checkConstraint(numberArray[key.getValue0()], numberArray[key.getValue1()], constraintsMap.get(key))){
                return false;
            }
        }
        return true;
    }

    private boolean checkConstraint(int valueA, int valueB, char sign){
        if(valueA == -1 || valueB == -1) return true;
        if(sign == '>'){
            return valueA > valueB;
        }
        else {
            return valueA < valueB;
        }
    }

    public boolean isComplete() {
        return complete;
    }

    public List<FutoshikiPossibility> spawnChildren(){
        ArrayList<FutoshikiPossibility> result = new ArrayList<>();
        ArrayList<FutoshikiPossibility> generatedPossibilities = new ArrayList<>();
        int changedIndex = ArrayUtils.indexOf(numberArray, -1);
        for(int i = 0; i < domain.length; i++){
            int[] copyArray = numberArray.clone();
            copyArray[changedIndex] = domain[i];
            generatedPossibilities.add(new FutoshikiPossibility(copyArray, changedIndex, constraintsMap, domain));
        }
        for (int i = 0; i < generatedPossibilities.size(); i++) {
            if(generatedPossibilities.get(i).valid) result.add(generatedPossibilities.get(i));
        }
        return result;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                stringBuilder.append(numberArray[i*dim + j]).append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
