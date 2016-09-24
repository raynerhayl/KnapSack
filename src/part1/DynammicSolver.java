package part1;

import helpers.KnapSackHelpers;
import helpers.Parcel;
import helpers.Solver;

import java.util.*;

public class DynammicSolver extends Solver {

    private int[][] dyArray;

    public DynammicSolver(List<Parcel> parcelList) {
        super(parcelList);
    }

    public List<Parcel> solve(int maxWeight){
        System.out.println("Running 0-1 using Dynammic Implementation... ");
        List<Parcel> solution;

        initArray(maxWeight);
        optimize();
        solution = getSolution();

        return solution;
    }


    /**
     * Initiate an array to store the subProblems given
     * some list of Parcel objects.
     */
    private void initArray(int maxWeight) {
        dyArray = new int[maxWeight + 1][parcelList.size()];
    }

    private void optimize() {
        int maxWeight = dyArray.length;
        for (int subMaxWeight = 0; subMaxWeight < maxWeight; subMaxWeight++) {
            for (int subParcel = 0; subParcel < parcelList.size(); subParcel++) {

                int subWeight = parcelList.get(subParcel).getWeight();
                int subValue = parcelList.get(subParcel).getValue();

                int subSolution = 0;

                if (subMaxWeight == 0) {
                    dyArray[subMaxWeight][subParcel] = 0;
                } else if (subParcel == 0) {
                    if (subWeight <= subMaxWeight) {
                        subSolution = subValue;
                    }
                } else if (subWeight > subMaxWeight) {
                    subSolution = dyArray[subMaxWeight][subParcel - 1];
                } else {
                    int previousSubSolution = dyArray[subMaxWeight][subParcel - 1]; // solution to problem if this parcel where not added
                    int nextLowestSubProblem = subMaxWeight - subWeight; // subProblem if this parcel where to be added

                    int nextLowestSubSolution = subValue + dyArray[nextLowestSubProblem][subParcel - 1]; // solution to above suProblem

                    subSolution = Math.max(previousSubSolution, nextLowestSubSolution);
                }

                dyArray[subMaxWeight][subParcel] = subSolution;

            }
        }
    }

    /**
     * Back tracks through the array of subProblems, constructing the
     * solution of parcels given the same parcelList used to optimize
     * the subProblems.
     *
     */
    private List<Parcel> getSolution() {
        List<Parcel> copy = KnapSackHelpers.copyList(parcelList);

        int solutionWeight = dyArray[dyArray.length - 1][dyArray[0].length - 1];
        int subMaxWeight = dyArray.length - 1;
        int subParcel = dyArray[0].length - 1;

        while (subMaxWeight >= 0 && subParcel >= 0) {

            if (subMaxWeight == 0) {
                parcelList.get(subParcel).setStatus(false); // finished subProblem without this parcel
            } else if (subParcel == 0) {
                if (parcelList.get(subParcel).getWeight() < subMaxWeight) { // first subProblem may have non zero maxWeight
                    copy.get(subParcel).incNum();
                }
            } else {
                solutionWeight = dyArray[subMaxWeight][subParcel];
                int previousSolution = dyArray[subMaxWeight][subParcel - 1]; // previous solution, without accepting the current subParcel
                if (previousSolution == solutionWeight) {
                    copy.get(subParcel).setStatus(false); // parcel isnt in solution
                } else {
                    copy.get(subParcel).incNum(); // parcel is in the solution
                    int previousSubMaxWeight = subMaxWeight - parcelList.get(subParcel).getWeight();
                    subMaxWeight = previousSubMaxWeight;
                }
            }
            subParcel = subParcel - 1;
        }
        return copy;
    }

}
