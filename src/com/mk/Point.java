package com.mk;

public class Point
{
    private static float degToRad(float angle)
    {
        return (float) (angle * Math.PI / 180);
    }

    public static Vector2 point3D(Vector3 pos, Camera cam, int w, int h)
    {
        Vector3 pos3 = new Vector3();
        Vector2 pos2 = new Vector2();
        pos3.setCoords((pos.getX()) - cam.getPos().getX(),
                (pos.getY()) - cam.getPos().getY(),
                (pos.getZ()) - cam.getPos().getZ());
        if(pos3.getZ() <= 0) pos3.setZ(.01f);
        pos2.setCoords(pos3.getX() / (pos3.getZ() / cam.getConstI()),pos3.getY() / (pos3.getZ() / cam.getConstI()));
        pos2.setCoords(pos2.getX() + cam.getCenter().getX(), pos2.getY() + cam.getCenter().getY());
        /*if(pos2.getX() < 0)
            pos2.setX(-5);
        if(pos2.getX() > w)
            pos2.setX(w + 5);
        if(pos2.getY() < 0)
            pos2.setY(-5);
        if(pos2.getY() > h)
            pos2.setY(h + 5);*/
        return pos2;
    }

    public static Vector3 rotate(Vector3 origin, Vector3 pointLocation, Vector3 angle)
    {
        Vector3 rad = new Vector3(degToRad(angle.getX()), degToRad(angle.getY()), degToRad(angle.getZ()));

        /*float tmp = rad.getX();
        rad.setX(rad.getY());
        rad.setY(-tmp);*/

        float pitch = rad.getX() * 0.5f;
        float sinPitch = (float) Math.sin(pitch);
        float cosPitch = (float) Math.cos(pitch);
        Quaternion qPitch = new Quaternion(cosPitch, sinPitch, 0, 0);

        float yaw = rad.getY() * 0.5f;
        float sinYaw = (float) Math.sin(yaw);
        float cosYaw = (float) Math.cos(yaw);
        Quaternion qYaw = new Quaternion(cosYaw, 0, sinYaw, 0);

        float roll = rad.getZ() * 0.5f;
        float sinRoll = (float) Math.sin(roll);
        float cosRoll = (float) Math.cos(roll);
        Quaternion qRoll = new Quaternion(cosRoll, 0, 0, sinRoll);

        Quaternion q = qYaw.multiply(qPitch).multiply(qRoll);

        float qLength = (float) Math.sqrt(q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        q.x /= qLength;
        q.y /= qLength;
        q.z /= qLength;
        q.w /= qLength;

        float[] pointVector = {pointLocation.getX() - origin.getX(), pointLocation.getY() - origin.getY(), pointLocation.getZ() - origin.getZ(), 1};

        Quaternion qPoint = new Quaternion(0, pointVector[0], pointVector[1], pointVector[2]);
        Quaternion qResult = q.multiply(qPoint).multiply(q.conjugate());

        Vector3 newLocation = new Vector3();
        newLocation.setX(qResult.x + origin.getX());
        newLocation.setY(qResult.y + origin.getY());
        newLocation.setZ(qResult.z + origin.getZ());

        return newLocation;
    }
}
