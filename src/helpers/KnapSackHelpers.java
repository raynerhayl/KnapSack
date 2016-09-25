package helpers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Haylem on 19/09/2016.
 */
public class KnapSackHelpers {


    public static String printKnapSack(List<Parcel> parcels) {
        int[] maxValues = getMaxStringSize(parcels);
        int maxWidth = maxValues[0] + maxValues[1] + maxValues[1];
        String st = append("", "-", maxValues[0]).concat("W-");
        st = append(st, "-", maxValues[1] - 1).concat("V-");
        st = append(st, "-", maxValues[2] - 1).concat("N-\n");

        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = parcels.get(i);
            String weight = String.format("%" + maxValues[0] + "d", parcel.getWeight());
            String value = String.format("%" + maxValues[1] + "d", parcel.getValue());
            String num = String.format("%" + maxValues[2] + "d", parcel.getNum());

            st = st.concat(" " + weight + " " + value + " " + num + "\n");
        }

        return st;
    }

    public static String append(String st, String toAppend, int iterations) {
        String result = "".concat(st);
        for (int i = 0; i < iterations; i++) {
            result = result.concat(toAppend);
        }
        return result;
    }

    /**
     * Finds the maximum lengths of each parcel property.
     *
     * @param parcels
     * @return
     */
    private static int[] getMaxStringSize(List<Parcel> parcels) {
        int[] maxValues = new int[]{-1,-1,-1};
        for (Parcel parcel : parcels) {
            maxValues[0] = (maxValues[0] < String.valueOf(parcel.getWeight()).length()) ? String.valueOf(parcel.getWeight()).length() : maxValues[0];
            maxValues[1] = (maxValues[1] < String.valueOf(parcel.getValue()).length()) ? String.valueOf(parcel.getValue()).length() : maxValues[1];
            maxValues[2] = (maxValues[2] < String.valueOf(parcel.getNum()).length()) ? String.valueOf(parcel.getNum()).length() : maxValues[2];
        }
        return maxValues;
    }

    public static List<Parcel> loadParcels(String filename) {
        List<Parcel> parcels = new ArrayList<Parcel>();

        try {
            File f = (new File(filename));
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

    public void printText(String text, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.print(text);
            writer.close();
        } catch (IOException e) {

        }
    }

    public static List<Parcel> loadParcels() {
        return loadParcels("res/parcels.txt");
    }

    public static List<Parcel> copyList(List<Parcel> parcelList) {
        List<Parcel> copy = new ArrayList<Parcel>();
        for (int i = 0; i < parcelList.size(); i++) {
            copy.add(new Parcel(parcelList.get(i)));
        }
        return copy;
    }

}
