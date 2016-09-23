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

    public Barometer(String filename){
        this.filename = filename;
        try{
           writer = new PrintWriter(filename, "UTF-8");
        } catch(IOException e){
            System.out.println(e);
        }
    }

    public void resetInput(int input){
        if(cost >= 0){
            writer.println(this.input+", "+cost);
            writer.flush();
        }
        this.input = input;
        cost = 0;
    }

    public void close(){
        writer.close();
    }


}
