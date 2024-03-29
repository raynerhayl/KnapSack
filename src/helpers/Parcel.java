package helpers;

import java.lang.*;

/**
 * Represents some entity with a weight and value. Can be
 * compared according to increasing weight.
 *
 * Created by Haylem on 18/09/2016.
 */
public class Parcel implements Comparable<Parcel> {


    private int weight;
    private int value;
    private int num = 0;
    private boolean status;

    public Parcel(int weight,int value) {
        this.weight = weight;
        this.value = value;
        status = false;
    }

    public Parcel(Parcel parcel){
        this.weight = parcel.getWeight();
        this.value = parcel.getValue();
        this.status = parcel.isStatus();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int compareTo(Parcel toCompare){
        return this.value-toCompare.getValue();
    }

    public void incNum(){
        this.num ++;
    }

    public void setNum(int num){
        this.num = num;
        if(num > 0) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
        }
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Weight: "+this.weight +" Value: "+this.value;
    }
}
