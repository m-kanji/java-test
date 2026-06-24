package com.mk;

public class Vector3
{
    private float x, y, z;

    public Vector3()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v3)
    {
        x = v3.x;
        y = v3.y;
        z = v3.z;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }

    public void setCoords(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setCoords(Vector3 v3)
    {
        x = v3.x;
        y = v3.y;
        z = v3.z;
    }

    @Override
    public String toString() { return "Vector3 -> { X = " + x + ", Y = " + y + " Z = " + z + " }"; }
}