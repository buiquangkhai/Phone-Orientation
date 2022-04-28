package com.example.assignment1;

public class Gyroscope {
    private float x, y, z;

    public Gyroscope(float x_, float y_, float z_){
        this.x = x_;
        this.y = y_;
        this.z = z_;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getZ(){
        return z;
    }
    public void setX(float x_){
        this.x = x_;
    }
    public void setY(float y_){  this.y = y_; }
    public void setZ(float z_){  this.z = z_; }

    @Override
    public String toString() {
        return "Gyroscope{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
