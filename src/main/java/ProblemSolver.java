import models.BinaryProblem;
import models.FutoshikiPossibility;
import models.FutoshikiProblem;
import models.BinaryPossibility;
import services.DataLoader;

import java.io.IOException;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) throws IOException {
        BinaryProblem bp = new BinaryProblem(DataLoader.loadBinaryData("src/main/resources/binary_10x10"), new int[]{1, 2});
        List<BinaryPossibility> results = bp.generateResults();
        results.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------------------");
        long startTime = System.currentTimeMillis();
        FutoshikiProblem fp = new FutoshikiProblem(DataLoader.loadFutoshikiData("src/main/resources/futoshiki_6x6"));
        List<FutoshikiPossibility> fpResults = fp.generateResults();
        System.out.println(fpResults.size());
        fpResults.forEach(res -> System.out.println(res+ "-------------------------------------------"));
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) /1000);


    }
}
