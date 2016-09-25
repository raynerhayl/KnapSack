package helpers;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Haylem on 22/09/2016.
 */
public class Barometer {
    String filename;
    PrintWriter writer;

    int cost = -1;
    int input = 0;

    public Barometer(String filename, int input){
        this.filename = filename;
        this.input = input;
        try{
           writer = new PrintWriter(filename, "UTF-8");
        } catch(IOException e){
            System.out.println(e);
        }
    }

    public void resetInput(int input){
        System.out.println("RESETING");
        if(cost >= 0){
            writer.println(this.input+", "+cost);
            writer.flush();
        }
        this.input = input;
        cost = 0;
    }

    public void incrementCost(){
        cost++;
    }

    public void close(){
        writer.close();
    }


}
