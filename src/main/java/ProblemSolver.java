import models.*;
import services.DataLoader;
import variablechoice.LeastDomainSize;
import variablechoice.LeastIndexFirst;


import java.io.IOException;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) throws IOException {
//        Problem bp = new Problem(new BinaryPossibility(DataLoader.loadBinaryData("src/main/resources/binary_6x6"), false), new LeastIndexFirst());
//        List<Possibility> results = bp.generateResults();
//        results.forEach(System.out::println);
//        System.out.println("----------------------------------------------------------------------------");
        long startTime = System.currentTimeMillis();
        var data = DataLoader.loadFutoshikiData("src/main/resources/futoshiki_6x6");
        Problem fp = new Problem(new FutoshikiPossibility(data.getValue0(), data.getValue1(), true), new LeastDomainSize());
        List<Possibility> fpResults = fp.generateResults();
        System.out.println(fpResults.size());
        fpResults.forEach(res -> System.out.println(res+ "-------------------------------------------"));
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) /1000);


    }
}
