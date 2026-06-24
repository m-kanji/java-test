package com.mk.shape;

import com.mk.Area;
import com.mk.Vector3;

import java.awt.*;

public class Shape
{
    protected Vector3[] points;
    protected Vector3 pos;
    protected Vector3 scale;
    protected Vector3 rotation;
    /*protected Camera cam;*/
    protected Image sprite;

    Shape(Vector3 pos, Vector3 scale, Vector3 rotation, int d/*, Camera cam*/)
    {
        this.pos = pos;
        this.scale = scale;
        this.rotation = rotation;
        points = new Vector3[d];
        for(int i = 0; i < points.length; i++)
            points[i] = new Vector3();
        /*this.cam = cam;*/
    }

    public Vector3 getPos() { return pos; }
    public Vector3 getScale() { return scale; }
    public Vector3[] getPoints() { return points; }

    protected interface GettersSetters
    {
        void setPos(Vector3 pos);
        void setScale(Vector3 scale);
        void updatePoints();
        Area getArea();
    }
}
