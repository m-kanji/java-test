package com.mk.shape;

import com.mk.Area;
import com.mk.Point;
import com.mk.Vector3;

public class Plane extends Shape implements Shape.GettersSetters
{
    public Plane(Vector3 pos, Vector3 scale, Vector3 rotation/*, Camera cam*/)
    {
        super(pos, scale, rotation, 8/*, cam*/);
        updatePoints();
    }
    public Plane(Vector3 pos, Vector3 scale/*, Camera cam*/)
    {
        super(pos, scale, new Vector3(), 8/*, cam*/);
        updatePoints();
    }

    @Override
    public void setPos(Vector3 pos)
    {
        this.pos = pos;
        updatePoints();
    }

    @Override
    public void setScale(Vector3 scale)
    {
        this.scale = scale;
        updatePoints();
    }

    @Override
    public void updatePoints()
    {
        if(pos == null)
            pos = new Vector3();
        if(scale == null)
            scale = new Vector3(1, 1, 1);
        Vector3 distCenterSide = new Vector3(scale.getX() / 2, scale.getY() / 2, 0);
        points[0].setCoords(-distCenterSide.getX() + pos.getY(), -distCenterSide.getY() + pos.getY(), pos.getZ());
        points[1].setCoords(distCenterSide.getX() + pos.getY(), -distCenterSide.getY() + pos.getY(), pos.getZ());
        points[2].setCoords(distCenterSide.getX() + pos.getY(), distCenterSide.getY() + pos.getY(), pos.getZ());
        points[3].setCoords(-distCenterSide.getX() + pos.getY(), distCenterSide.getY() + pos.getY(), pos.getZ());
        for(Vector3 p : points) p.setCoords(Point.rotate(pos, p, rotation));
    }

    @Override
    public Area getArea()
    {
        return new Area(points[0], points[2]);
    }
}
