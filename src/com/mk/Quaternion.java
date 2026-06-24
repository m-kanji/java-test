package com.mk;

public class Quaternion
{
    float w, x, y, z;

    public Quaternion(float w, float x, float y, float z)
    {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternion conjugate() { return new Quaternion(w, -x, -y, -z); }

    public Quaternion multiply(Quaternion q)
    {
        float w = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
        float x = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
        float y = this.w * q.y + this.y * q.w + this.z * q.x - this.x * q.z;
        float z = this.w * q.z + this.z * q.w + this.x * q.y - this.y * q.x;
        return new Quaternion(w, x, y, z);
    }

    public float getW() { return w; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public void setW(float w) { this.w = w; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
}
