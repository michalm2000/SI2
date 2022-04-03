package models;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class BinaryPossibility {
    private int[] numberArray;
    private int dim;
    private boolean valid;
    private boolean complete;

    public BinaryPossibility(int[] numberArray) {
        this.numberArray = numberArray;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = true;
    }

    public BinaryPossibility(int[] numArray, int changedIndex){
        this.numberArray = numArray;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = checkRow(changedIndex/ dim) && checkColumn( changedIndex % dim);
    }


    public boolean checkRow(int rowNumber){
        for (int i = 1; i < dim - 1; i++){
            int preceding = numberArray[rowNumber*dim + i - 1];
            int current = numberArray[rowNumber*dim + i];
            int following = numberArray[rowNumber*dim + i + 1];

            if(preceding >= 0 && current >= 0 && following >= 0 && preceding == current  && current == following){
                return false;
            }
        }

        int counter0 = 0;
        int counter1 = 0;
        for(int i = rowNumber*dim; i < rowNumber*dim + dim; i++){
            if(numberArray[i] == 0){
                counter0++;
            }
            if(numberArray[i] == 1){
                counter1++;
            }
        }
        if (counter0 > dim/2 || counter1 > dim/2) return false;


        return true;
    }
    public boolean checkColumn(int colNumber){
        for (int i = 1; i < dim - 1; i++) {
            int preceding = numberArray[(i - 1) * dim + colNumber];
            int current = numberArray[i * dim + colNumber];
            int following = numberArray[(i + 1) * dim + colNumber];

            if (preceding >= 0 && current >= 0 && following >= 0 && preceding == current && current == following) {
                return false;
            }
        }

        int counter0 = 0;
        int counter1 = 0;
        for(int i = 0; i < dim; i++){
            if(numberArray[i*dim + colNumber] == 0){
                counter0++;
            }
            if(numberArray[i*dim + colNumber] == 1){
                counter1++;
            }
        }
        if (counter0 > dim/2 || counter1 > dim/2) return false;

        return true;
    }


    public boolean isComplete() {
        return complete;
    }

    public List<BinaryPossibility> spawnChildren(){
        int[] copyArray0 = numberArray.clone();
        int[] copyArray1 = numberArray.clone();
        int changedIndex = ArrayUtils.indexOf(copyArray0, -1);
        copyArray0[changedIndex] = 0;
        copyArray1[changedIndex] = 1;
        BinaryPossibility pos1 = new BinaryPossibility(copyArray0, changedIndex);
        BinaryPossibility pos2 = new BinaryPossibility(copyArray1, changedIndex);
        ArrayList<BinaryPossibility> result = new ArrayList<>();

        if(pos1.valid) result.add(pos1);
        if(pos2.valid) result.add(pos2);
        return result;
    }

    @Override
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
