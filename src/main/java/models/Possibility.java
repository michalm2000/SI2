package models;

import org.javatuples.Pair;
import variablechoice.VariableChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Possibility {
    boolean checkColumn(int[]arr, int column);
    boolean checkRow(int[] arr, int column);
    boolean checkConstraints();

    boolean isComplete();
    Pair<ArrayList<Possibility>, Integer> spawnChildren(VariableChoice variableChoice);

    @Override
    String toString();
    int[] getNumberArray();
    Map<Integer,List<Integer>> getDomainMap();
    int getDim();
    Map<Pair<Integer, Integer>, Character> getConstraintsMap();
    boolean isForwardChecking();
}
