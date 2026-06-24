package com.mk;

public class Area
{
    private Vector3 minPos;
    private Vector3 maxPos;

    public Area(Vector3 p1, Vector3 p2)
    {
        updateEdges(p1, p2);
    }

    public Area(Area area)
    {
        minPos = area.minPos;
        maxPos = area.maxPos;
    }

    public void setArea(Area area)
    {
        minPos = area.minPos;
        maxPos = area.maxPos;
    }

    public void setArea(Vector3 p1, Vector3 p2)
    {
        updateEdges(p1, p2);
    }

    void updateEdges(Vector3 p1, Vector3 p2)
    {
        minPos = new Vector3();
        maxPos = new Vector3();
        if(p1.getX() <= p2.getX())
        {
            minPos.setX(p1.getX());
            maxPos.setX(p2.getX());
        }
        else
        {
            minPos.setX(p2.getX());
            maxPos.setX(p1.getX());
        }
        if(p1.getY() <= p2.getY())
        {
            minPos.setY(p1.getY());
            maxPos.setY(p2.getY());
        }
        else
        {
            minPos.setY(p2.getY());
            maxPos.setY(p1.getY());
        }
        if(p1.getZ() <= p2.getZ())
        {
            minPos.setZ(p1.getZ());
            maxPos.setZ(p2.getZ());
        }
        else
        {
            minPos.setZ(p2.getZ());
            maxPos.setZ(p1.getZ());
        }
    }

    public boolean intersects(Area area)
    {
        return
        minPos.getX() <= area.maxPos.getX() &&
        maxPos.getX() >= area.minPos.getX() &&
        minPos.getY() <= area.maxPos.getY() &&
        maxPos.getY() >= area.minPos.getY() &&
        minPos.getZ() <= area.maxPos.getZ() &&
        maxPos.getZ() >= area.minPos.getZ();
    }
}
