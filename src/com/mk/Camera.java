package com.mk;

public class Camera
{
    private float constI;
    private Vector3 pos;
    private Vector2 center;

    Camera()
    {
        constI = 500;
        pos = new Vector3(0, 0, 0);
        center = new Vector2(0, 0);
    }

    Camera(Vector3 pos, Vector2 center, float constI)
    {
        this.constI = constI;
        this.pos = pos;
        this.center = center;
    }

    public float getConstI() { return constI; }
    public Vector3 getPos() { return pos; }
    public Vector2 getCenter() { return center; }
    void setConstI(float constI) { this.constI = constI; }
    void setPos(Vector3 pos) { this.pos = pos; }
    void setPos(float x, float y, float z) { this.pos.setCoords(x, y, z); }
    void setCenter(Vector2 center) { this.center = center; }
    void setCenter(float x, float y) { this.center.setCoords(x, y); }
}
