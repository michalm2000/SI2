package models;

import java.util.List;

public interface Solver {
    boolean isComplete();
    List<Solver> spawnResults();
    @Override
    String toString();


}
