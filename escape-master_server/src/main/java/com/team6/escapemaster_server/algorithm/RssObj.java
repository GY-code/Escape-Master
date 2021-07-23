package com.team6.escapemaster_server.algorithm;

import java.util.ArrayList;

class Position{
    double x,y;
    @Override
    public String toString(){
        return x+","+y;
    }
}

public class RssObj implements Comparable<RssObj>{
    public ArrayList<Double> signalAVGs = new ArrayList<>();
    public ArrayList<Double> signalVARs = new ArrayList<>();
    public double distance;
    public Position pos=new Position();

    public RssObj(){

    }

    @Override
    public int compareTo(RssObj o) {
        return (int)(this.distance-o.distance);
    }
}

