package part1;

import helpers.Parcel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private int[][] dyArray;

    public Main(){
        List<Parcel> parcels = Arrays.asList(new Parcel[]{new Parcel(1,1), new Parcel(3,4), new Parcel(4,5),new Parcel(5,7)});

        Collections.sort(parcels);

        System.out.println("Parcel Array: ");

        for(Parcel parcel: parcels){
            System.out.println(parcel);
        }

        System.out.println("\nOptimizing ... ");

        initArray(parcels,7);
        optimize(parcels);

        for(int i = 0; i < dyArray[0].length; i++){
            for(int j = 0; j < dyArray.length; j++){
                System.out.print(dyArray[j][i] + " ");
            }
            System.out.println("");
        }

    }

    /**
     * Initiate an array to store the subProblems given
     * some list of Parcel objects.
     *
     * @param parcelList a list of parcels sorted in increasing order by weight
     */
    private void initArray(List<Parcel> parcelList, int maxWeight) {
        dyArray = new int[maxWeight+1][parcelList.size()];
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
                } else if(subWeight > subMaxWeight) {
                    subSolution = dyArray[subMaxWeight][subParcel-1];
                } else{
                    int previousSubSolution = dyArray[subMaxWeight][subParcel - 1]; // solution to problem if this parcel where not added
                    int nextLowestSubProblem = subMaxWeight - subWeight; // subProblem if this parcel where to be added

                    System.out.println("subWeight: "+subWeight + " subMaxWeight: "+subMaxWeight);

                    int nextLowestSubSolution = subValue + dyArray[nextLowestSubProblem][subParcel - 1]; // solution to above suProblem

                    subSolution = Math.max(previousSubSolution,nextLowestSubSolution);
                }

                dyArray[subMaxWeight][subParcel] = subSolution;

            }
        }
    }

    public static void main(String[] args) {

        new Main();

    }

}
