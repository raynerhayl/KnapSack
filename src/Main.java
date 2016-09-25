import helpers.*;
import part1.DynammicSolver;
import part2.EnumerateSolver;
import part3.ExtendedDynammicSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Haylem on 23/09/2016.
 */
public class Main {

    public Main() {
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;
        while (repeat == true) {
            if (askBoolean(scanner, "Run tests or continue to main program")) {
                Tester.generateTestFile("test.txt",18,20,10,3);
            } else {
                List<Parcel> parcelList;
                if (askBoolean(scanner, "Use provided file or default one.")) {
                    System.out.println("Enter file name .txt ");
                    parcelList = KnapSackHelpers.loadParcels(getFile(scanner));
                } else {
                    parcelList = KnapSackHelpers.loadParcels();
                }
                List<Parcel> solutionList = new ArrayList<>();

                int maxWeight = askInt(scanner, "Enter maximum weight");

                Barometer barometer = new Barometer("dynammicBarometer.txt", maxWeight);
                Solver solver = new DynammicSolver(parcelList, barometer);

                if (askBoolean(scanner, "Run 0-1 KnapSack Assumption")) {

                    if (askBoolean(scanner, "Solve using dynammic solution (Y) or enumeration (N)")) {
                        solver = new DynammicSolver(parcelList, barometer);
                    } else {
                        solver = new EnumerateSolver(parcelList, false);
                    }
                } else {
                    if (askBoolean(scanner, "Solve using dynammic solution (Y) or enumeration (N)")) {
                        solver = new ExtendedDynammicSolver(parcelList);
                    } else {
                        solver = new EnumerateSolver(parcelList, true);
                    }
                }

                solutionList = solver.solve(maxWeight);
                barometer.resetInput(maxWeight);
                barometer.close();

                int solutionValue = solutionList.remove(solutionList.size() - 1).getValue();

                printSolutions(solutionList);
                System.out.println("V = " + solutionValue);


                repeat = askBoolean(scanner, "Repeat program ");
            }
        }
    }

    public void printSolutions(List<Parcel> solutions) {
        String toPrint = KnapSackHelpers.printKnapSack(solutions);
        System.out.println("\n" + toPrint + "\n");
    }

    public boolean askBoolean(Scanner scanner, String question) {
        String result = "";
        while (result.equalsIgnoreCase("y") == false && result.equalsIgnoreCase("n") == false) {
            System.out.println(question + " (Y/N) ?");
            result = scanner.next().toLowerCase();
        }

        if (result.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    public int askInt(Scanner scanner, String question) {
        String result = "null";
        while (result.matches("\\d+") == false) {
            System.out.println(question + " (Y/N) ?");
            result = scanner.next();
        }
        return Integer.valueOf(result);
    }

    public String getFile(Scanner scanner) {
        String fileName = "";
        while (fileName.endsWith(".txt")) {
            System.out.println("Please enter file name of .txt with list of parcels: \n");
            fileName = scanner.next();
        }
        return fileName;
    }


    public static void main(String[] args) {
        new Main();
    }

}
