package helpers;

import part2.EnumerateSolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haylem on 25/09/2016.
 */
public class Tester {

    public static void generateTestFile(String filename, int maxCost, int maxValue, int maxWeight, int numParcels) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            List<Parcel> parcelPool = generateParcels(maxCost,maxValue,numParcels);

            for(int i = 1; i < numParcels; i++){
                System.out.println("OUTERFOR");
                List<Parcel> parcelList = new ArrayList<>();
                for(int j = 1; j < i; j++){
                    System.out.println("INNERFOR");
                    parcelList.add(parcelPool.get((int)(Math.random()*i)));
                }
                EnumerateSolver solver = new EnumerateSolver(parcelList, true);
                List<Parcel> solution = solver.getSolution();

                int solutionValue = solution.remove(solution.size()-1).getValue();
                String knapSack = KnapSackHelpers.printKnapSack(solution);
                knapSack= knapSack.concat(String.valueOf(solutionValue));

                writer.println(knapSack);
                writer.flush();
            }

            writer.close();
        } catch (IOException e) {

        }

    }

    private static List<Parcel> generateParcels(int maxCost, int maxValue, int numParcels) {
        List<Parcel> parcels = new ArrayList<>();
        for (int i = 0; i < numParcels; i++) {
            int cost = (int) (Math.random() * maxCost);
            int value = (int) (Math.random() * maxValue);

            parcels.add(new Parcel(cost, value));
        }
        return parcels;
    }

}
