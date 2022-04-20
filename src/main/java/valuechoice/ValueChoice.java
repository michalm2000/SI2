package valuechoice;

import models.Possibility;

import java.util.List;

public interface ValueChoice {
    void sortDomain(Possibility possibility, int changedIndex);
}
