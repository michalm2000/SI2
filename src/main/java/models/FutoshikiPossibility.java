package models;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.Pair;
import services.DomainUtils;
import variablechoice.VariableChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class FutoshikiPossibility implements Possibility {
    private int[] numberArray;
    private int dim;
    private boolean valid;
    private boolean complete;
    private Map<Pair<Integer, Integer>, Character> constraintsMap;
    private Map<Integer, List<Integer>> domainMap;
    private boolean forwardChecking;



    public FutoshikiPossibility(int[] numberArray, Map<Pair<Integer, Integer>, Character> constraintsMap, boolean forwardChecking) {
        this.numberArray = numberArray;
        this.constraintsMap = constraintsMap;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = true;
        domainMap = DomainUtils.createDomainMap(this);
        this.forwardChecking = forwardChecking;
        if (forwardChecking) DomainUtils.updateMapForwardChecking(this);


    }

    public FutoshikiPossibility(int[] numArray, int changedIndex, Map<Pair<Integer, Integer>, Character> constraintsMap, Map<Integer, List<Integer>> domainMap, boolean forwardChecking) {
        this.numberArray = numArray;
        this.constraintsMap = constraintsMap;
        dim = (int) Math.sqrt(numberArray.length);
        complete = ArrayUtils.indexOf(numberArray, -1) == -1;
        valid = checkRow(numberArray, changedIndex / dim) && checkColumn(numberArray, changedIndex % dim) && checkConstraints(numberArray);
        this.domainMap = domainMap;
        this.forwardChecking = forwardChecking;
    }

    public boolean checkRow(int[] arr, int rowNumber) {
        int[] rowWithNumbers = Arrays.stream(Arrays.copyOfRange(arr, rowNumber * dim, (rowNumber + 1) * dim))
                .filter(c -> c != -1).toArray();
        if (rowWithNumbers.length <= 1) return true;
        return Arrays.stream(rowWithNumbers).distinct().count() == rowWithNumbers.length;

    }

    public boolean checkColumn(int[] arr, int colNumber) {
        int[] col = new int[dim];
        for (int i = 0; i < dim; i++) {
            col[i] = arr[i * dim + colNumber];
        }
        int[] colWithNumbers = Arrays.stream(col).filter(c -> c != -1).toArray();
        if (colWithNumbers.length <= 1) return true;
        return Arrays.stream(colWithNumbers).distinct().count() == colWithNumbers.length;

    }

    public boolean checkConstraints(int[] arr) {
        for (var key : constraintsMap.keySet()) {
            if(constraintsMap.get(key) == null) System.out.println("dupa");
            if (!checkConstraint(arr[key.getValue0()], arr[key.getValue1()], constraintsMap.get(key)))
                return false;
        }
        return true;
    }

    public boolean checkConstraint(int valueA, int valueB, char sign) {
        if (valueA == -1 || valueB == -1) return true;
        if (sign == '>') {
            return valueA > valueB;
        } else {
            return valueA < valueB;
        }
    }

    public Pair<ArrayList<Possibility>, Integer> spawnChildren(VariableChoice variableChoice) {
        int changedIndex = variableChoice.chooseVariable(this);
        ArrayList<Possibility> results = new ArrayList<>();
        for (int number: domainMap.get(changedIndex)) {
            int[] copyArray = numberArray.clone();
            copyArray[changedIndex] = number;
            FutoshikiPossibility pos;
            if(checkRow(copyArray, changedIndex / dim) && checkColumn(copyArray, changedIndex % dim) && checkConstraints(copyArray)) {
                if (forwardChecking) {
                    pos = new FutoshikiPossibility(copyArray, changedIndex, constraintsMap, domainMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue()))), true);
                DomainUtils.updateDomainRow(pos, changedIndex);
                DomainUtils.updateDomainColumn(pos, changedIndex);
                //DomainUtils.updateDomainConstraints(pos);

                } else {
                    pos = new FutoshikiPossibility(copyArray, changedIndex, constraintsMap, domainMap, false);
                }
                results.add(pos);
            }
        }
        return Pair.with(results, domainMap.get((changedIndex)).size());

    }




    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dim * 2 - 1; i++) {
            if (i % 2 == 0) {
                stringBuilder.append((numberArray[i / 2 * dim])).append(' ');
                for (int j = 1; j < dim; j++) {
                    char sign = constraintsMap.getOrDefault(Pair.with(i / 2 * dim + j - 1, i / 2 * dim + j), ' ');
                    stringBuilder.append(sign).append(' ');
                    stringBuilder.append(numberArray[i / 2 * dim + j]).append(' ');
                }
            } else {
                for (int j = 0; j < dim * 2 - 1; j++) {
                    if (j % 2 == 1) {
                        stringBuilder.append(' ');
                    } else {
                        char sign = constraintsMap.getOrDefault(Pair.with(i / 2 * dim + j / 2, (i / 2 + 1) * dim + j / 2), ' ');
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


