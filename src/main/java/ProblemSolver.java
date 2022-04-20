import models.*;
import services.DataLoader;
import valuechoice.Default;
import valuechoice.LeastInterference;
import variablechoice.LeastDomainSize;
import variablechoice.LeastIndexFirst;


import java.io.IOException;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Problem bp = new Problem(new BinaryPossibility(DataLoader.loadBinaryData("src/main/resources/binary_10x10"), false), new LeastIndexFirst(), new LeastInterference());
//        List<Possibility> results = bp.generateResults();
//        results.forEach(System.out::println);
        Possibility reslt = bp.generateResult();
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)/ (double) 1000);
        System.out.println("----------------------------------------------------------------------------");
        startTime = System.currentTimeMillis();
        var data = DataLoader.loadFutoshikiData("src/main/resources/futoshiki_6x6");
        Problem fp = new Problem(new FutoshikiPossibility(data.getValue0(), data.getValue1(), false), new LeastIndexFirst(), new LeastInterference());
        var fpResults = fp.generateResult();
        System.out.println(fpResults);
//        System.out.println(fpResults.size());
//        fpResults.forEach(res -> System.out.println(res+ "-------------------------------------------"));
        endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / (double)1000);

    }
}
