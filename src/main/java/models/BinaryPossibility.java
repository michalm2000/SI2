package models;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.Pair;
import services.DomainUtils;
import variablechoice.VariableChoice;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class BinaryPossibility implements Possibility {
    private int[] numberArray;
    private int dim;
    private boolean valid;
    private boolean complete;
    private Map<Integer, List<Integer>> domainMap;
    private boolean forwardChecking;


    public BinaryPossibility(int[] numberArray, boolean forwardChecking) {
        this.numberArray = numberArray;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = true;
        this.forwardChecking = forwardChecking;
        this.domainMap = DomainUtils.createDomainMap(this);
        if(forwardChecking) DomainUtils.updateMapForwardChecking(this);
    }

    public BinaryPossibility(int[] numArray, int changedIndex, Map<Integer, List<Integer>> domainMap, boolean forwardChecking){
        this.numberArray = numArray;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = checkRow(numberArray,changedIndex/ dim) && checkColumn(numberArray, changedIndex % dim);
        this.domainMap = domainMap;
        this.forwardChecking = forwardChecking;
    }


    public boolean checkRow(int[] arr, int rowNumber){
        for (int i = 1; i < dim - 1; i++){
            int preceding = arr[rowNumber*dim + i - 1];
            int current = arr[rowNumber*dim + i];
            int following = arr[rowNumber*dim + i + 1];
            if(preceding >= 0 && current >= 0 && following >= 0 && preceding == current  && current == following){
                return false;
            }
        }

        int counter0 = 0;
        int counter1 = 0;
        for(int i = rowNumber*dim; i < rowNumber*dim + dim; i++){
            if(arr[i] == 0){
                counter0++;
            }
            if(arr[i] == 1){
                counter1++;
            }
        }
        if (counter0 > dim/2 || counter1 > dim/2) return false;


        return true;
    }

    @Override
    public boolean checkConstraints(int[] arr) {
        return true;
    }


    public boolean checkColumn(int[] arr, int colNumber){
        for (int i = 1; i < dim - 1; i++) {
            int preceding = arr[(i - 1) * dim + colNumber];
            int current = arr[i * dim + colNumber];
            int following = arr[(i + 1) * dim + colNumber];

            if (preceding >= 0 && current >= 0 && following >= 0 && preceding == current && current == following) {
                return false;
            }
        }

        int counter0 = 0;
        int counter1 = 0;
        for(int i = 0; i < dim; i++){
            if(arr[i*dim + colNumber] == 0){
                counter0++;
            }
            if(arr[i*dim + colNumber] == 1){
                counter1++;
            }
        }
        if (counter0 > dim/2 || counter1 > dim/2) return false;

        return true;
    }




    public boolean isComplete() {
        return complete;
    }

    public Pair<ArrayList<Possibility>, Integer> spawnChildren(VariableChoice variableChoice){
        int changedIndex = variableChoice.chooseVariable(this);
        ArrayList<Possibility> result = new ArrayList<>();
        for (int number: domainMap.get(changedIndex)) {
            int[] copyArray = numberArray.clone();
            copyArray[changedIndex] = number;
            BinaryPossibility pos;
            if (forwardChecking) {
                pos = new BinaryPossibility(copyArray, changedIndex, domainMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue()))), true);
                DomainUtils.updateDomainRow(pos, changedIndex);
                DomainUtils.updateDomainColumn(pos, changedIndex);
                DomainUtils.updateDomainConstraints(pos);
            }
            else {
                pos = new BinaryPossibility(copyArray, changedIndex, domainMap, false);
            }
            if (pos.valid) result.add(pos);
        }
        return Pair.with(result, domainMap.get(changedIndex).size());
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

    @Override
    public Map<Pair<Integer, Integer>, Character> getConstraintsMap() {
        return null;
    }


}
