package models;


import variablechoice.VariableChoice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem {
    private Possibility startingPossibility;
    private int counter;
    private VariableChoice variableChoice;


    public Problem(Possibility startingPossibility, VariableChoice variableChoice){
        this.variableChoice = variableChoice;
        this.startingPossibility = startingPossibility;
        counter = 0;
    }


    public List<Possibility> generateResults() {
        int counter = 0;
        List<Possibility> possibilitiesToCheck = new LinkedList<>();
        List<Possibility> results = new ArrayList<>();
        possibilitiesToCheck.add(startingPossibility);
        while (!possibilitiesToCheck.isEmpty()){
            Possibility p = possibilitiesToCheck.get(0);
            if(p.isComplete()){
                results.add(p);
            }
            else {
                var res = p.spawnChildren(variableChoice);
                possibilitiesToCheck.addAll(res.getValue0());
                counter+=res.getValue1();
            }
            possibilitiesToCheck.remove(0);
        }
        System.out.println(counter);
        return results;
    }



}
