package helpers;

import java.util.List;

/**
 * Created by Haylem on 23/09/2016.
 */
public abstract class Solver {

    protected List<Parcel> parcelList;

    public Solver(List<Parcel> parcelList){
        this.parcelList = parcelList;
    }

    public abstract List<Parcel> solve(int maxWeight);

}
