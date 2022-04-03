package models;

import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FutoshikiPossibility {
    private int[] numberArray;
    private int dim;
    private boolean valid;
    private boolean complete;
    private Map<Pair<Integer, Integer>, Character> constraintsMap;

    public FutoshikiPossibility(int[] numberArray, Map<Pair<Integer, Integer>, Character> constraintsMap) {
        this.numberArray = numberArray;
        this.constraintsMap = constraintsMap;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = true;
    }

    public FutoshikiPossibility(int[] numArray, Map<Pair<Integer, Integer>, Character> constraintsMap, int changedIndex){
        this.numberArray = numArray;
        this.constraintsMap = constraintsMap;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = checkRow(changedIndex/ dim) && checkColumn( changedIndex % dim) && checkConstraints();
    }

    public boolean checkRow(int rowNumber){
        int[] rowWithNumbers = Arrays.stream(Arrays.copyOfRange(numberArray, rowNumber*dim, (rowNumber+1)*dim))
                                        .filter(c -> c != -1).toArray();
        if (rowWithNumbers.length <= 1) return true;
        return Arrays.stream(rowWithNumbers).distinct().count() == rowWithNumbers.length;

    }

    public boolean checkColumn (int colNumber){
        int[] col = new int [dim];
        for (int i = 0; i < dim; i++){
            col[i] = numberArray[i*dim + colNumber];
        }
        int [] colWithNumbers = Arrays.stream(col).filter(c -> c != -1).toArray();
        if (colWithNumbers.length <= 1) return true;
        return Arrays.stream(colWithNumbers).distinct().count() == colWithNumbers.length;

    }

    public boolean checkConstraints(){
        for (var key: constraintsMap.keySet()) {
            if(!checkConstraint(numberArray[key.getValue0()], numberArray[key.getValue1()], constraintsMap.get(key))) return false;
        }
        return true;
    }
    public boolean checkConstraint(int valueA, int valueB, char sign){
        if(valueA == -1 || valueB == -1) return true;
        if(sign == '>'){
            return valueA > valueB;
        }
        else{
            return valueA < valueB;
        }
    }

    public List<FutoshikiPossibility> spawnChildren(){
        int changedIndex = ArrayUtils.indexOf(numberArray, -1);
        List<FutoshikiPossibility> results = new ArrayList<>();
        for (int i = 0; i < dim; i++){
            int[] copyArray = numberArray.clone();
            copyArray[changedIndex] = i+1;
            FutoshikiPossibility pos = new FutoshikiPossibility(copyArray,constraintsMap, changedIndex);
            if (pos.valid) results.add(pos);
        }
        return results;

    }

    public boolean isComplete() {
        return complete;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < dim*2 -1; i++){
            if(i % 2 == 0) {
                stringBuilder.append((numberArray[i / 2 * dim])).append(' ');
                for (int j = 1; j < dim; j++) {
                    char sign = constraintsMap.getOrDefault(Pair.with(i / 2 * dim + j - 1, i / 2 * dim + j), ' ');
                    stringBuilder.append(sign).append(' ');
                    stringBuilder.append(numberArray[i / 2  * dim + j]).append(' ');
                }
            }
            else {
                for (int j = 0; j < dim*2 -1; j++){
                    if (j % 2 == 1 ){
                        stringBuilder.append(' ');
                    }
                    else {
                        char sign = constraintsMap.getOrDefault(Pair.with(i/2*dim + j/2, (i/2+1)*dim + j/2), ' ');
                        if (sign == '>') sign = 'v';
                        if (sign == '<') sign = '^';
                        stringBuilder.append(sign);
                    }
                    stringBuilder.append(' ');
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
