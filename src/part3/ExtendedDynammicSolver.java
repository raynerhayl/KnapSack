package part3;

import helpers.Barometer;
import helpers.KnapSackHelpers;
import helpers.Parcel;
import helpers.Solver;
import part1.DynammicSolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the 0-N Knapsack problem using a dynammic solution.
 * This works using the 0-1 Dynammic solution by parsing the
 * input list of parcels into one which contains an extended
 * number of parcels.
 *
 * Created by Haylem on 24/09/2016.
 */
public class ExtendedDynammicSolver extends Solver {

    private Barometer barometer;
    private List<Parcel> parsedList = new ArrayList<Parcel>();
    List<Integer> indexArray = new ArrayList<Integer>();

    public ExtendedDynammicSolver(List<Parcel> parcelList, Barometer barometer) {
        super(parcelList);
        this.barometer = barometer;
    }

    @Override
    public List<Parcel> solve(int maxWeight) {

        /*
        Create an extended parcelList to send to the dynammic solver.
        This extended list contains each parcel in the input list multiple
        times, as many times as the maximum possible number of those parcels
        could be in the final solution.
         */
        for (int i = 0; i < parcelList.size(); i++) {
            for (int j = 0; j < maxWeight / parcelList.get(i).getWeight(); j++) {
                parsedList.add(parcelList.get(i));
                indexArray.add(i);
            }
        }

        DynammicSolver solver = new DynammicSolver(parsedList, barometer);
        List<Parcel> simpleSolutionList = solver.solve(maxWeight);
        List<Parcel> solutionList = new ArrayList<Parcel>();

        for (int i = 0; i < parcelList.size(); i++) {
            solutionList.add(new Parcel(parcelList.get(i)));
        }

        Parcel solutionValue = simpleSolutionList.get(simpleSolutionList.size()-1);
        simpleSolutionList.remove(simpleSolutionList.size()-1);

        for (int i = 0; i < simpleSolutionList.size(); i++) {
            if (simpleSolutionList.get(i).getNum()>0) {
                solutionList.get(indexArray.get(i)).incNum();
            }
        }

        solutionList.add(solutionValue);

        return solutionList;
    }
}


