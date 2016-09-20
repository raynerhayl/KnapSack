package part1;

import helpers.KnapSackDisplay;
import helpers.Parcel;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private int[][] dyArray;

    public Main() {
        List<Parcel> parcels = loadParcels();//Arrays.asList(new Parcel[]{new Parcel(1, 1), new Parcel(3, 4), new Parcel(4, 5), new Parcel(5, 7)});

        //loadParcels();

        //Collections.sort(parcels);

        System.out.println("Parcel Array: ");

        for (Parcel parcel : parcels) {
            System.out.println(parcel);
        }

        System.out.println("\nOptimizing ... ");

        initArray(parcels, 18);
        optimize(parcels);

        for (int i = 0; i < dyArray[0].length; i++) {
            for (int j = 0; j < dyArray.length; j++) {
                System.out.print(dyArray[j][i] + " ");
            }
            System.out.println("");
        }

        System.out.println("\n");
        System.out.println("Printing Solution: \n");
        getSolution(parcels);

        String printOutput = KnapSackDisplay.printKnapSack(parcels);

        System.out.println(printOutput);

    }

    public List<Parcel> loadParcels() {
        List<Parcel> parcels = new ArrayList<Parcel>();

        try {
            File f = (new File("res/parcels.txt"));
            if (f.exists()) {

                Scanner scanner = new Scanner(f);
                while (scanner.hasNext()) {
                    parcels.add(new Parcel(scanner.nextInt(), scanner.nextInt()));
                }
            } else {
                System.out.println("File doesn't exist");
            }
        } catch (IOException e) {

        }

        return parcels;
    }

    /**
     * Initiate an array to store the subProblems given
     * some list of Parcel objects.
     *
     * @param parcelList a list of parcels sorted in increasing order by weight
     */
    private void initArray(List<Parcel> parcelList, int maxWeight) {
        dyArray = new int[maxWeight + 1][parcelList.size()];
    }

    private void optimize(List<Parcel> parcelList) {
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
     * @param parcelList the parcelList, sorted in increasing weight order
     *                   used to optimize the subProblems.
     */
    private void getSolution(List<Parcel> parcelList) {
        int solutionWeight = dyArray[dyArray.length - 1][dyArray[0].length - 1];
        int subMaxWeight = dyArray.length - 1;
        int subParcel = dyArray[0].length - 1;

        while (subMaxWeight >= 0 && subParcel >= 0) {

            if (subMaxWeight == 0) {
                parcelList.get(subParcel).setStatus(false); // finished subProblem without this parcel
            } else if (subParcel == 0) {
                if (parcelList.get(subParcel).getWeight() < subMaxWeight) { // first subProblem may have non zero maxWeight
                    parcelList.get(subParcel).incNum();
                }
            } else {
                solutionWeight = dyArray[subMaxWeight][subParcel];
                int previousSolution = dyArray[subMaxWeight][subParcel - 1]; // previous solution, without accepting the current subParcel
                if (previousSolution == solutionWeight) {
                    System.out.println(previousSolution + " " + solutionWeight);
                    parcelList.get(subParcel).setStatus(false); // parcel isnt in solution
                } else {
                    parcelList.get(subParcel).incNum(); // parcel is in the solution
                    int previousSubMaxWeight = subMaxWeight - parcelList.get(subParcel).getWeight();
                    subMaxWeight = previousSubMaxWeight;
                }
            }
            subParcel = subParcel - 1;
        }

    }

    public static void main(String[] args) {

        new Main();

    }

}
