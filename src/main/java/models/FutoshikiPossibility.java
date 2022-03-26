package models;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.javatuples.Pair;

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
    }

    public FutoshikiPossibility(int[] numArray, int changedIndex){
        this.numberArray = numArray;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = checkRow(changedIndex/ dim) && checkColumn( changedIndex % dim);
    }

    public boolean checkRow(int rowNumber){
        throw new NotImplementedException();
    }

    public boolean checkColumn (int colNumber){
        throw new NotImplementedException();
    }

    public boolean isComplete() {
        return complete;
    }

}
