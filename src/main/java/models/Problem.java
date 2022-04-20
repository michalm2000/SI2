package models;


import valuechoice.ValueChoice;
import variablechoice.VariableChoice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Problem {
    private Possibility startingPossibility;
    private int counter;
    private VariableChoice variableChoice;
    private ValueChoice valueChoice;
    public static int backtrackCount;


    public Problem(Possibility startingPossibility, VariableChoice variableChoice, ValueChoice valueChoice){
        this.variableChoice = variableChoice;
        this.startingPossibility = startingPossibility;
        counter = 0;
        this.valueChoice = valueChoice;
    }


    public List<Possibility> generateResults() {
        backtrackCount = 0;
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
                var res = p.spawnChildren(variableChoice,valueChoice );
                possibilitiesToCheck.addAll(res.getValue0());
                counter+=res.getValue1();
            }
            possibilitiesToCheck.remove(0);
        }
        System.out.println("backtracks: " + backtrackCount);
        System.out.println("nodes: " + counter);
        return results;
    }

    public Possibility generateResult (){
        int counter = 0;
        List<Possibility> possibilitiesToCheck = new LinkedList<>();
        possibilitiesToCheck.add(startingPossibility);
        while (!possibilitiesToCheck.isEmpty()){
            Possibility p = possibilitiesToCheck.get(0);
            if(p.isComplete()){
                System.out.println("backtracks: " + backtrackCount);
                System.out.println("nodes: " + counter);
                return p;
            }
            else {
                var res = p.spawnChildren(variableChoice, valueChoice);
                possibilitiesToCheck.addAll(1, res.getValue0());
                counter+=res.getValue1();
            }
            possibilitiesToCheck.remove(0);

        }
        System.out.println(counter);
        return null;
    }

}
