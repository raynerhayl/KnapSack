package part2;

import helpers.KnapSackHelpers;
import helpers.Parcel;
import helpers.Solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haylem on 23/09/2016.
 */
public class EnumerateSolver extends Solver {

    public EnumerateSolver(List<Parcel> parcelList){
        super(parcelList);
    }

    public List<Parcel> solve(int maxWeight){
        List<Parcel> solution = new ArrayList<Parcel>();
        List<Parcel> parsedList = new ArrayList<Parcel>(); // contain an enumerated selection of parcels

        for(int i = 0; i < parsedList.size(); i++){
            for(int j = 0; j < maxWeight/parcelList.get(i).getWeight(); j++){
                parsedList.add(parcelList.get(i));
            }
        }

        // permutate the parsedList and optimize result

        return solution;
    }

}
