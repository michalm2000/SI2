import models.BinaryProblem;
import models.Possibility;
import services.DataLoader;

import java.io.IOException;
import java.util.List;

public class ProblemSolver {

    public static void main(String[] args) throws IOException {
//        BinaryProblem bp = new BinaryProblem(DataLoader.loadBinaryData("src/main/resources/binary_6x6"), new int[]{1, 2});
//        List<Possibility> results = bp.generateResults();
//        results.forEach(System.out::println);
        DataLoader.loadFutoshikiData("src/main/resources/futoshiki_4x4");

    }
}
