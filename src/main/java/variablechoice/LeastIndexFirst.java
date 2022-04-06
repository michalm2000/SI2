package variablechoice;

import models.Possibility;
import org.apache.commons.lang3.ArrayUtils;

public class LeastIndexFirst implements VariableChoice {

    @Override
    public int chooseVariable(Possibility possibility) {
        return ArrayUtils.indexOf(possibility.getNumberArray(), -1);

    }
}
