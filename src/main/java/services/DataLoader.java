package services;

import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    public static int[] loadBinaryData(String filePath) throws IOException {
        return Files.readString(
                Paths.get(filePath)).chars().filter(c -> c != 13 && c != 10)
                .map(c -> c > 63 ? -1 : Character.getNumericValue(c)).toArray();
    }

    public static Pair<int[], Map<Pair<Integer, Integer>, Character>> loadFutoshikiData(String filePath) throws IOException{
        List<String> lineList = Files.readAllLines(Paths.get(filePath));
        int dim = lineList.get(0).length()/2+1;
        int[] numberArray = new int[dim*dim];
        HashMap<Pair<Integer, Integer>, Character> constraintsMap = new HashMap<>();
        for(int i = 0; i < lineList.size(); i++){
            var charArray = lineList.get(i).toCharArray();
            for(int j = 0; j < charArray.length; j++){
                if(i % 2 == 0) {
                    if (j % 2 == 0) {
                        numberArray[i/2*dim + j/2] = Character.getNumericValue(charArray[j]);
                    } else {
                        if (charArray[j] != '-')
                            constraintsMap.put(new Pair<>(i/2*dim+j/2, i/2*dim + j/2 + 1), charArray[j]);
                    }
                }
                else {
                    if (charArray[j] != '-'){
                        constraintsMap.put(new Pair<>(i/2*dim + j, ((i+1)/2)*dim + j), charArray[j]);
                    }
                }
            }
        }
        System.out.println(Arrays.toString(numberArray));
        System.out.println(constraintsMap);
        return new Pair<>(numberArray, constraintsMap);
    }
}
