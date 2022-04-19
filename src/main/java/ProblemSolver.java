import models.*;
import services.DataLoader;
import variablechoice.LeastDomainSize;
import variablechoice.LeastIndexFirst;


import java.io.IOException;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Problem bp = new Problem(new BinaryPossibility(DataLoader.loadBinaryData("src/main/resources/binary_6x6"), true), new LeastDomainSize());
        List<Possibility> results = bp.generateResults();
        results.forEach(System.out::println);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println("----------------------------------------------------------------------------");
        startTime = System.currentTimeMillis();
        var data = DataLoader.loadFutoshikiData("src/main/resources/futoshiki_5x5");
        Problem fp = new Problem(new FutoshikiPossibility(data.getValue0(), data.getValue1(), false), new LeastDomainSize());
        var fpResults = fp.generateResults();
        System.out.println(fpResults.size());
        fpResults.forEach(res -> System.out.println(res+ "-------------------------------------------"));
        System.out.println(fpResults);
        endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / (double)1000);


    }
}
