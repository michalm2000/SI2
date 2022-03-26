package models;

import java.util.List;

public interface Solver {
    boolean isComplete();
    List<? extends Solver> spawnResults();
    @Override
    String toString();


}
