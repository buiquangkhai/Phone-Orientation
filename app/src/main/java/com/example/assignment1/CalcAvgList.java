package com.example.assignment1;

import java.util.List;

public class CalcAvgList {
    public static float[] calcAvgAcc(List<Accelerometer> linkedList) {
        float[] avg = new float[3];
        float sumX = 0, sumY = 0, sumZ = 0;
        for (int i = 0; i < linkedList.size(); i++) {
            sumX += linkedList.get(i).getX();
            sumY += linkedList.get(i).getY();
            sumZ += linkedList.get(i).getZ();
        }
        avg[0] = sumX / linkedList.size();
        avg[1] = sumY / linkedList.size();
        avg[2] = sumZ / linkedList.size();
        return avg;
    }

    public static float[] calcAvgCom(List<Compass> linkedList) {
        float[] avg = new float[3];
        float sumX = 0, sumY = 0, sumZ = 0;
        for (int i = 0; i < linkedList.size(); i++) {
            sumX += linkedList.get(i).getX();
            sumY += linkedList.get(i).getY();
            sumZ += linkedList.get(i).getZ();
        }
        avg[0] = sumX / linkedList.size();
        avg[1] = sumY / linkedList.size();
        avg[2] = sumZ / linkedList.size();
        return avg;
    }

    public static float[] calcAvgGyro(List<Gyroscope> linkedList) {
        float[] avg = new float[3];
        float sumX = 0, sumY = 0, sumZ = 0;
        for (int i = 0; i < linkedList.size(); i++) {
            sumX += linkedList.get(i).getX();
            sumY += linkedList.get(i).getY();
            sumZ += linkedList.get(i).getZ();
        }
        avg[0] = sumX / linkedList.size();
        avg[1] = sumY / linkedList.size();
        avg[2] = sumZ / linkedList.size();
        return avg;
    }
}
