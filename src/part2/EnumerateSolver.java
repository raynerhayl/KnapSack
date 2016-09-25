package part2;

import helpers.KnapSackHelpers;
import helpers.Parcel;
import helpers.Solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solves the 0-N Knapsack problem using enumeration.
 * <p>
 * Created by Haylem on 23/09/2016.
 */
public class EnumerateSolver extends Solver {

    List<int[]> permutations = new ArrayList<int[]>(); // arrays of int represent number of each parcel in candidate solution
    int maxWeight;

    boolean polyEnumeration = false; // when set to false only one of each parcel allowed in the solution

    public EnumerateSolver(List<Parcel> parcelList, boolean polyEnumeration) {
        super(parcelList);
        this.polyEnumeration = polyEnumeration;
    }

    public List<Parcel> solve(int maxWeight) {
        if(polyEnumeration){
            System.out.println("Solving 0-N using enumeration...");
        } else{
            System.out.println("Solving 0-1 using enumeration... ");
        }

        this.maxWeight = maxWeight;

        Set<Integer> toPermutate = new HashSet<Integer>();
        for (int i = 0; i < parcelList.size(); i++) {
            toPermutate.add(i);
        }

        int[] permutation = new int[parcelList.size()];

        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = -1;
        }

        permutation(permutation, toPermutate);

        return getSolution();
    }

    public List<Parcel> getSolution() {
        List<Parcel> solution = new ArrayList<Parcel>();

        int maxValue = 0;
        int maxIndex = 0;
        for (int i = 0; i < permutations.size(); i++) {
            int value = 0;
            int weight = 0;
            boolean skip = false;
            for (int j = 0; j < permutations.get(i).length; j++) {
                if (permutations.get(i)[j] > 0) {
                    skip = true;
                }
                value += parcelList.get(j).getValue() * permutations.get(i)[j];
                weight += parcelList.get(j).getWeight() * permutations.get(i)[j];
            }

            if (value > maxValue && weight < maxWeight) {
                maxValue = value;
                maxIndex = i;
            }
        }

        for (int i = 0; i < parcelList.size(); i++) {
            solution.add(parcelList.get(i));
            solution.get(i).setNum(permutations.get(maxIndex)[i]);
        }

        solution.add(new Parcel(0,maxValue));

        return solution;
    }

    /**
     * Calculate all candidate solutions
     *
     * @param permutation the current permutation of parcels
     * @param toPermutate the indicies of parcels in parcel list which still need to be added to the permutation
     */
    public void permutation(int[] permutation, Set<Integer> toPermutate) {
        if (toPermutate.isEmpty()) {
            permutations.add(permutation);
        } else {
            for (int i : toPermutate) {
                int subWeight = 0;
                int increment = 0;
                int recIncrement = 1;
                while ((subWeight < maxWeight) && ((polyEnumeration == false && increment <= 1) || (polyEnumeration))) {
                    int[] recPermutation = new int[permutation.length];
                    for (int recIndex = 0; recIndex < recPermutation.length; recIndex++) {
                        recPermutation[recIndex] = permutation[recIndex];
                        if (recIndex == i) {
                            recPermutation[recIndex] = permutation[recIndex] + recIncrement;
                        }
                    }

                    subWeight = recPermutation[i] * parcelList.get(i).getWeight();

                    Set<Integer> recPermutate = new HashSet<Integer>();
                    for (int toCopy : toPermutate) {
                        if (toCopy != i) {
                            recPermutate.add(toCopy);
                        }
                    }

                    permutation(recPermutation, recPermutate);
                    increment++;
                    recIncrement++;
                }
            }
        }
    }

}
