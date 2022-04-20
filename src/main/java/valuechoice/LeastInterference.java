package valuechoice;

import models.Possibility;

import java.util.Comparator;
import java.util.HashMap;

public class LeastInterference implements ValueChoice{

    @Override
    public void sortDomain(Possibility possibility, int changedIndex) {
        var domainMap = possibility.getDomainMap();
        var domainToSort = possibility.getDomainMap().get(changedIndex);
        HashMap<Integer, Integer> occurenceMap = new HashMap<>();
        for(int i = changedIndex / possibility.getDim() * possibility.getDim(); i < changedIndex / possibility.getDim() * possibility.getDim() + possibility.getDim(); i++){
            var value = possibility.getNumberArray()[i];
            if (i != changedIndex){
                if (occurenceMap.containsKey(value)){
                    occurenceMap.put(value, occurenceMap.get(value) + 1);
                }
                else {
                    occurenceMap.put(value, 1);
                }
            }
        }
        for(int i = changedIndex % possibility.getDim() ; i < possibility.getDim() *  possibility.getDim(); i+= possibility.getDim()){
            var value = possibility.getNumberArray()[i];
            if (i != changedIndex){
                if (occurenceMap.containsKey(value)){
                    occurenceMap.put(value, occurenceMap.get(value) + 1);
                }
                else {
                    occurenceMap.put(value, 1);
                }
            }
        }
        domainToSort.sort(Comparator.comparingInt(e -> occurenceMap.getOrDefault(e, 0)));
    }
}
