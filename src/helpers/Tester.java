package helpers;

import part1.DynammicSolver;
import part2.EnumerateSolver;
import part3.ExtendedDynammicSolver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Haylem on 25/09/2016.
 */
public class Tester {

    public static void generateTestFile(String filename, int maxCost, int maxValue, int maxWeight, int numParcels, boolean polyEnumeration, int numTests, boolean solve) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            List<Parcel> parcelPool = generateParcels(maxCost, maxValue, numTests);

            writer.println(polyEnumeration);


            for (int i = 1; i <= numTests; i++) {
                List<Parcel> parcelList = new ArrayList<>();
                Set<Integer> selected = new HashSet<>();

                int parcelSize = (i < numParcels) ? i : numParcels;

                for (int j = 0; j < parcelSize; j++) {
                    int index = (int) (Math.random() * numTests);
                    while (selected.contains(index)) {
                        index = (int) (Math.random() * numTests);
                    }
                    selected.add(index);
                    parcelList.add(parcelPool.get(index)); // shouldn't have repeats
                    System.out.println("3");
                }

                EnumerateSolver solver = new EnumerateSolver(parcelList, polyEnumeration);
                List<Parcel> solution;
                if (solve) {
                    solution = solver.solve(maxWeight);
                } else {
                    solution = parcelList;
                }

                int solutionValue = 0;
                if (solve) {
                    solutionValue = (solution.size() > 0) ? solution.remove(solution.size() - 1).getValue() : 0;
                }

                String knapSack = KnapSackHelpers.printKnapSack(solution);

                knapSack = knapSack.concat("V= " + String.valueOf(solutionValue));
                writer.println(knapSack);
                writer.flush();
            }

            writer.close();
        } catch (IOException e) {

        }

    }

    public static List<List<Parcel>> testSolver(String filename, int maxCost, Solver solverType) {
        List<List<Parcel>> failedLists = new ArrayList<>();

        Barometer barometer = new Barometer("barometer_" + filename, 0);

        try {
            Scanner scanner = new Scanner(new File(filename));
            boolean polyEnumeration = scanner.nextBoolean();
            while (scanner.hasNext()) {
                List<Parcel> parcelList = new ArrayList<Parcel>();
                List<Parcel> masterSolution = new ArrayList<Parcel>();
                while (scanner.hasNextInt()) {
                    int weight = scanner.nextInt();
                    int value = scanner.nextInt();

                    int num = 0;
                    String numString = scanner.nextLine();
                    if (numString.length() > 0) {
                        num = Integer.valueOf(numString.trim());
                    }

                    masterSolution.add(new Parcel(weight, value));
                    masterSolution.get(masterSolution.size() - 1).setNum(num);

                    parcelList.add(new Parcel(weight, value));
                }
                String token = scanner.next();
                if (token.startsWith("V")) {

                    int solutionValue = scanner.nextInt();

                    Solver solver = null;

                    if (solverType.getClass() == EnumerateSolver.class) {
                        solver = new EnumerateSolver(parcelList, polyEnumeration);
                    } else if (polyEnumeration == true) {
                        solver = new ExtendedDynammicSolver(parcelList, barometer);
                    } else {
                        solver = new DynammicSolver(parcelList, barometer);
                    }

                    List<Parcel> potentialSolution = solver.solve(maxCost);
                    barometer.resetInput(parcelList.size());

                    int potentialValue = potentialSolution.remove(potentialSolution.size() - 1).getValue();

                    System.out.println("Expected Solution: ");
                    System.out.println(KnapSackHelpers.printKnapSack(potentialSolution));
                    System.out.println("V= " + potentialValue);

                    System.out.println("Actual Solution: ");
                    System.out.println(KnapSackHelpers.printKnapSack(masterSolution));
                    System.out.println("V= " + solutionValue);

                    if (potentialValue != solutionValue) {
                        failedLists.add(masterSolution);
                    }

                }
                scanner.nextLine();
                System.out.println("");
            }
            scanner.close();
        } catch (IOException e) {

        }

        return failedLists;
    }

    private static List<Parcel> generateParcels(int maxCost, int maxValue, int numParcels) {
        List<Parcel> parcels = new ArrayList<>();
        for (int i = 0; i < numParcels; i++) {
            int cost = Math.max((int) (Math.random() * maxCost), 1);
            int value = Math.max((int) (Math.random() * maxValue), 1);

            parcels.add(new Parcel(cost, value));
        }
        return parcels;
    }

}
