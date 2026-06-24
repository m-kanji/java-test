package com.mk;

public class Vector2
{
    private float x, y;

    Vector2()
    {
        x = 0;
        y = 0;
    }

    Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    Vector2(Vector2 v2)
    {
        x = v2.x;
        y = v2.y;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    public void setCoords(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void setCoords(Vector2 v2)
    {
        x = v2.x;
        y = v2.y;
    }

    @Override
    public String toString() { return "Vector3 -> { X = " + x + ", Y = " + y + " }"; }
}