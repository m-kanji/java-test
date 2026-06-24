package com.mk;

import com.mk.shape.Cube;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

public class GameManager extends Thread implements Runnable
{
    private static boolean gameActiveStatus = true;
    private float movForce = .05f;
    private static double delay = 25;

    public static Cube cubeTest;


    private static Camera cam;
    private static final ArrayList<GameObject> obstacles = new ArrayList<>(), bullets = new ArrayList<>();

    private static boolean wm, am, sm, dm, spm, sfm;

    private static int bulletReloadTime = 2000;//10000; //250 - 10000
    private static int bulletReloadStatus = bulletReloadTime;
    private static boolean autoFire = false;

    private static int obstaclesGenTime = 1000;
    private static int obstaclesGenStatus = obstaclesGenTime;

    private static long tick = 0;

    @Override
    public void run()
    {
        long oldTime = System.currentTimeMillis(), diff, sleep;

        while(gameActiveStatus)
        {
            diff = System.currentTimeMillis() - oldTime;
            sleep = (long) (delay - diff);
            if (sleep < 0) sleep = 2;
            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            }
            //System.out.println("a");
            movementMgr();
            if (spm || autoFire)
            {
                //cam.getPos().setY(cam.getPos().getY() - force);
                if(bulletReloadStatus <= 0)
                {
                    shoot();
                    bulletReloadStatus = bulletReloadTime;
                }
            }
            if(obstaclesGenStatus <= 0)
            {
                genObstacle();
                obstaclesGenStatus = obstaclesGenTime;
            }
            if(bulletReloadStatus > 0) bulletReloadStatus -= delay;
            if(obstaclesGenStatus > 0) obstaclesGenStatus -= delay;
            for(int i = 0, r = 0; i < bullets.size() - r; i++)
            {
                GameObject g = bullets.get(i);
                ((Cube) g.getShape()).setPos(new Vector3(g.getShape().getPos().getX(), g.getShape().getPos().getY(), g.getShape().getPos().getZ() + 1f));
                if(g.getShape().getPos().getZ() > 250 || ((Cube) g.getShape()).getArea().intersects(cubeTest.getArea()))
                {
                    bullets.remove(i);
                    r++;
                }
            }
            try
            {
                for (GameObject g : obstacles)
                    ((Cube) g.getShape()).setPos(new Vector3(g.getShape().getPos().getX(), g.getShape().getPos().getY(), g.getShape().getPos().getZ() - .4f));
            }
            catch (ConcurrentModificationException e)
            {
                System.err.println("Conc Mod Ex");
            }
            i+=.2f;
            cubeTest = new Cube(new Vector3(0, 0, 10), new Vector3(1, 1, 1), new Vector3(i, i, 0));
            tick++;
        }
    } float i = 0;

    public static void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) wm = true;
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) sm = true;
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) dm = true;
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) am = true;
        if (key == KeyEvent.VK_SPACE) spm = true;
        if (key == KeyEvent.VK_SHIFT) sfm = true;
    }

    public static void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) wm = false;
        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) sm = false;
        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) dm = false;
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) am = false;
        if (key == KeyEvent.VK_E) autoFire = !autoFire;
        if (key == KeyEvent.VK_SPACE) spm = false;
        if (key == KeyEvent.VK_SHIFT) sfm = false;
    }

    void movementMgr()
    {
        if (wm && cam.getPos().getY() > -9)
        {
            //cam.getPos().setZ(cam.getPos().getZ() + force);
            cam.getPos().setY(cam.getPos().getY() - movForce);
        }
        if (sm && cam.getPos().getY() < 9)
        {
            //cam.getPos().setZ(cam.getPos().getZ() - force);
            cam.getPos().setY(cam.getPos().getY() + movForce);
        }
        if (dm && cam.getPos().getX() < 9)
        {
            cam.getPos().setX(cam.getPos().getX() + movForce);
        }
        if (am && cam.getPos().getX() > -9)
        {
            cam.getPos().setX(cam.getPos().getX() - movForce);
        }
        if (sfm)
        {
            cam.getPos().setY(cam.getPos().getY() + movForce);
        }
    }

    void shoot()
    {
        //System.out.println("Cam pos: " + cam.getPos().getX() + ", " + cam.getPos().getY() + ", " + cam.getPos().getZ());
        bullets.add(new GameObject(new Cube(new Vector3(cam.getPos().getX(), cam.getPos().getY() + 1, cam.getPos().getZ()), new Vector3(.25f, .25f, 1)), Type.YourBullet));
    }

    void genObstacle()
    {
        float rng = (float) Math.random();
        Random r = new Random();
        Type goType;
        Vector3 size;
        if(rng < .7f) {             goType = Type.Asteroid; size = new Vector3(3, 3, 3); }
        else if(rng < .9f) {        goType = Type.Block;    size = new Vector3(7, 7, 7); }
        else if(rng < .95f) {       goType = Type.UFO;      size = new Vector3(1, 1, 1); }
        else {                      goType = Type.Null;     size = new Vector3(0, 0, 0); }
        obstacles.add(new GameObject(new Cube(new Vector3(r.nextInt(-9, 9), r.nextInt(-9, 9), 250), size), goType));
    }

    public GameManager(Display d)
    {
        cubeTest = new Cube(new Vector3(0, 0, 10), new Vector3(1, 1, 1), new Vector3(0, 0, 0));
        cam = new Camera(new Vector3(), new Vector2(d.getCenterX(), d.getCenterY()), 500);
        BulletManager bm = new BulletManager();
        bm.start();
    }

    public static Camera getCam() { return cam; }
    public static boolean getGameActiveStatus() { return gameActiveStatus; }
    public static void setGameActiveStatus(boolean activeStatus) { gameActiveStatus = activeStatus; }
    public static ArrayList<GameObject> getObstacles() { return obstacles; }
    public static ArrayList<GameObject> getBullets() { return bullets; }
    public static long getTick() { return tick; }
    public static double getDelay() { return delay; }
}

class BulletManager extends Thread
{
    private static final ArrayList<GameObject> obstacles = GameManager.getObstacles();
    private static final ArrayList<GameObject> bullets = GameManager.getBullets();
    private final double delay = GameManager.getDelay();

    static void bulletCheker()
    {
        for(int i = 0, r0 = 0; i < obstacles.size() - r0; i++)
        {
            GameObject g = obstacles.get(i);
            if(g.getShape().getPos().getZ() <= 0)
            {
                obstacles.remove(i);
                r0++;
            }
            for(int j = 0, r1 = 0; j < bullets.size() - r1 - 1; j++)
            {
                if(((Cube) g.getShape()).getArea().intersects(((Cube) bullets.get(j).getShape()).getArea()))
                {
                    bullets.remove(j);
                    if(g.getType() == Type.Asteroid) obstacles.remove(i);
                    r0++;
                    r1++;
                }
            }
        }
    }

    @Override
    public void run()
    {
        long oldTime = System.currentTimeMillis(), diff, sleep;

        while(GameManager.getGameActiveStatus())
        {
            diff = System.currentTimeMillis() - oldTime;
            sleep = (long) (delay - diff);
            if (sleep < 0) sleep = 2;
            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
            }
            bulletCheker();
        }
    }
}