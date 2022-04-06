package variablechoice;

import models.Possibility;

public class LeastDomainSize implements VariableChoice{
    @Override
    public int chooseVariable(Possibility possibility) {
        int maxSize = Integer.MAX_VALUE;
        int index = -1;
        for (int key: possibility.getDomainMap().keySet()){
            if (maxSize > possibility.getDomainMap().get(key).size() && possibility.getNumberArray()[key] ==-1){
                maxSize = possibility.getDomainMap().get(key).size();
                index = key;
            }
            if(maxSize == 1) return index;
        }
        return index;
    }
}
