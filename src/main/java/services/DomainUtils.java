package services;

import models.BinaryPossibility;
import models.FutoshikiPossibility;
import models.Possibility;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class DomainUtils {
    public static void updateDomainColumn(Possibility possibility, int index){
        int dim = possibility.getDim();
        int[] numberArray = possibility.getNumberArray();
        var domainMap = possibility.getDomainMap();
        for(int i = index% dim; i < numberArray.length; i+=dim){
            if(i != index && numberArray[i] == -1){
                List<Integer> currentDomain = domainMap.get(i);
                List<Integer> invalidDomainValues = new ArrayList<>();
                int[] copyArray = numberArray.clone();
                for(int j = 0; j < currentDomain.size(); j++){
                    copyArray[i] = currentDomain.get(j);
                    if(!possibility.checkColumn(copyArray, i /dim)){
                        invalidDomainValues.add(copyArray[i]);
                    }
                }
                currentDomain.removeAll(invalidDomainValues);
            }
        }
    }
    public static void updateDomainRow(Possibility possibility, int index){
        int dim = possibility.getDim();
        int[] numberArray = possibility.getNumberArray();
        var domainMap = possibility.getDomainMap();

        for(int i = index/dim * dim; i < index/dim* dim + dim; i++){
            if(i != index && numberArray[i] == -1){
                List<Integer> currentDomain = domainMap.get(i);
                List<Integer> invalidDomainValues = new ArrayList<>();
                int[] copyArray = numberArray.clone();
                for(int j = 0; j < currentDomain.size(); j++){
                    copyArray[i] = currentDomain.get(j);
                    if(!possibility.checkRow(copyArray, i /dim)){
                        invalidDomainValues.add(copyArray[i]);
                    }
                }
                currentDomain.removeAll(invalidDomainValues);
            }
        }

    }

    public static void updateDomainConstraints(Possibility possibility){
        var constraintsMap = possibility.getConstraintsMap();
        if (constraintsMap == null) return;
        int[] numberArray = possibility.getNumberArray();
        var domainMap = possibility.getDomainMap();

        for (int i = 0; i < numberArray.length; i++){
            if (numberArray[i] == -1) {
                var currentDomain = domainMap.get(i);
                ArrayList<Integer> invalidDomainValues = new ArrayList<>();
                int[] copyArray = numberArray.clone();
                for (int j = 0; j < currentDomain.size(); j++){
                    copyArray[i] = currentDomain.get(j);
                    if(!possibility.checkConstraints()){
                        invalidDomainValues.add(currentDomain.get(j));
                    }
                }
                currentDomain.removeAll(invalidDomainValues);
            }
        }
    }

    public static HashMap<Integer,List<Integer>> createDomainMap(Possibility possibility){
        int[] numberArray = possibility.getNumberArray();
        int dim = possibility.getDim();
        HashMap<Integer,List<Integer>> domainMap = new HashMap<>();
        var emptyIndexes = ArrayUtils.indexesOf(numberArray, -1);
        for (int i = 0; i < numberArray.length; i++){
            if (emptyIndexes.get(i)){
                if (possibility instanceof BinaryPossibility) domainMap.put(i, new LinkedList<>(List.of(0,1)));
                else if (possibility instanceof FutoshikiPossibility) domainMap.put(i, new LinkedList<>(IntStream.range(1, dim + 1 ).boxed().toList()));
            }
        }
        return domainMap;
    }

    public static void updateMapForwardChecking(Possibility possibility){
        int[] numberArray = possibility.getNumberArray();
        var emptyIndexes = ArrayUtils.indexesOf(numberArray, -1);
        if (possibility.isForwardChecking()) {
            for (int i = 0; i < numberArray.length; i++) {
                if (emptyIndexes.get(i)) {
                    DomainUtils.updateDomainRow(possibility, i);
                    DomainUtils.updateDomainColumn(possibility, i);
                }
            }
        }
    }
}
