package com.mk;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.mk.shape.*;

import java.util.ConcurrentModificationException;

public class Display extends JPanel implements Runnable
{

    //private final int B_WIDTH = 350;
    //private final int B_HEIGHT = 350;
    private final int delay = 25;
    private Image star;
    private Thread animator;
    private int w, h;
    float zOffset, zOffsetInc, zOffsetMod;

    public Display(int w, int h) {
        this.w = w;
        this.h = h;
        zOffset = 0;
        zOffsetInc = -2;
        //zOffsetMod = 16;
        zOffsetMod = 25;
        initBoard();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/resources/star.png");
        star = ii.getImage();
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(w, h));

        loadImage();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }
    int i;

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        drawGrid(g, -10, 10, 250, 5, GameManager.getCam(), new Color(255, 0, 0, 0), new Color(0, 255, 0, 0), new Color(0, 0, 255), zOffset);
        g.setColor(new Color(255, 255, 255));
        //g.drawLine(-5, 2, 70, 70);
        g.setColor(new Color(255, 0, 255));
        GameObject go = new GameObject(GameManager.cubeTest, Type.Null);    //cube
        drawWireCube(g, go, GameManager.getCam());                          //cube
        //g.setColor(new Color(255, 0, 0));
        try
        {
            for (GameObject obstacle : GameManager.getObstacles()) {
                if (obstacle.type == Type.Asteroid)
                    g.setColor(new Color(0, 192, 192));
                else if (obstacle.type == Type.Block)
                    g.setColor(new Color(128, 128, 128));
                drawWireCube(g, obstacle, GameManager.getCam());
            }
            g.setColor(new Color(0, 255, 0));
            for (GameObject bullet : GameManager.getBullets())
                drawWireCube(g, bullet, GameManager.getCam());
        }
        catch (ConcurrentModificationException e)
        {
            System.err.println("Current Mod Ex");
        }
        g.setColor(new Color(255, 255, 255));
        drawCrossair(g, GameManager.getCam());
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void run()
    {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (GameManager.getGameActiveStatus())
        {
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = delay - timeDiff;
            if (sleep < 0) sleep = 2;
            zOffset = (zOffset + zOffsetInc) % zOffsetMod;//zOffset + GameManager.getTick() % zOffsetMod;
            try
            {
                Thread.sleep(17);//sleep);
            }
            catch (InterruptedException e)
            {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
            }
            beforeTime = System.currentTimeMillis();
        }
    }

    int getCenterX()
    {
        return Math.round((float) w / 2);
    }

    int getCenterY()
    {
        return Math.round((float) h / 2);
    }

    /// Seus ///

    void drawCrossair(Graphics g, Camera cam)
    {
        int centerX = (int) cam.getCenter().getX();
        int centerY = (int) cam.getCenter().getY() + 5;
        g.drawLine(centerX - 5, centerY, centerX + 5, centerY);
        g.drawLine(centerX, centerY - 5, centerX, centerY + 5);
    }

    void drawWireCube(Graphics g, GameObject cube, Camera cam)
    {
        for(int i = 0; i < 3; i++)
        {
            g.drawLine(Math.round(Point.point3D(cube.shape.getPoints()[i], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[i], cam, w, h).getY()), Math.round(Point.point3D(cube.shape.getPoints()[i + 1], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[i + 1], cam, w, h).getY()));
            g.drawLine(Math.round(Point.point3D(cube.shape.getPoints()[i + 4], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[i + 4], cam, w, h).getY()), Math.round(Point.point3D(cube.shape.getPoints()[i + 5], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[i + 5], cam, w, h).getY()));
            g.drawLine(Math.round(Point.point3D(cube.shape.getPoints()[i], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[i], cam, w, h).getY()), Math.round(Point.point3D(cube.shape.getPoints()[i + 4], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[i + 4], cam, w, h).getY()));
        }
        g.drawLine(Math.round(Point.point3D(cube.shape.getPoints()[3], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[3], cam, w, h).getY()), Math.round(Point.point3D(cube.shape.getPoints()[0], cam, 350, 350).getX()), Math.round(Point.point3D(cube.shape.getPoints()[0], cam, w, h).getY()));
        g.drawLine(Math.round(Point.point3D(cube.shape.getPoints()[7], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[7], cam, w, h).getY()), Math.round(Point.point3D(cube.shape.getPoints()[4], cam, 350, 350).getX()), Math.round(Point.point3D(cube.shape.getPoints()[4], cam, w, h).getY()));
        g.drawLine(Math.round(Point.point3D(cube.shape.getPoints()[3], cam, w, h).getX()), Math.round(Point.point3D(cube.shape.getPoints()[3], cam, w, h).getY()), Math.round(Point.point3D(cube.shape.getPoints()[7], cam, 350, 350).getX()), Math.round(Point.point3D(cube.shape.getPoints()[7], cam, w, h).getY()));

    }

    void drawWirePlane(Graphics g, GameObject plane, Camera cam)
    {
        for(int i = 0; i < 3; i++)
        {
            g.drawLine(Math.round(Point.point3D(plane.shape.getPoints()[i], cam, w, h).getX()), Math.round(Point.point3D(plane.shape.getPoints()[i], cam, w, h).getY()), Math.round(Point.point3D(plane.shape.getPoints()[i + 1], cam, w, h).getX()), Math.round(Point.point3D(plane.shape.getPoints()[i + 1], cam, w, h).getY()));
        }
        g.drawLine(Math.round(Point.point3D(plane.shape.getPoints()[3], cam, w, h).getX()), Math.round(Point.point3D(plane.shape.getPoints()[3], cam, w, h).getY()), Math.round(Point.point3D(plane.shape.getPoints()[0], cam, w, h).getX()), Math.round(Point.point3D(plane.shape.getPoints()[0], cam, w, h).getY()));
    }

    void drawGrid(Graphics g, int gridStart, int gridEnd, int gridDepth, int gridDist, Camera cam, Color xColor, Color yColor, Color zColor, float zOffset)
    {
        for(int i = gridStart; i <= gridEnd; i+=gridDist)
        {
            //g.drawLine(Math.round(Point.point3D(new Vector3(i, 0, 0), cam, 350, 350).getX()), Math.round(Point.point3D(new Vector3(i, 0, 0), cam, 350, 350).getY()), Math.round(Point.point3D(new Vector3(0, i, 100), cam, 350, 350).getX()), Math.round(Point.point3D(new Vector3(0, i, 0), cam, 350, 350).getY()));
            g.setColor(xColor);
            g.drawLine(Math.round(Point.point3D(new Vector3(i, gridStart, 0), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(i, gridStart, 0), cam, w, h).getY()), Math.round(Point.point3D(new Vector3(i, gridStart, gridDepth), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(i, gridStart, gridDepth), cam, w, h).getY()));
            g.drawLine(Math.round(Point.point3D(new Vector3(i, gridEnd, 0), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(i, gridEnd, 0), cam, w, h).getY()), Math.round(Point.point3D(new Vector3(i, gridEnd, gridDepth), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(i, gridEnd, gridDepth), cam, w, h).getY()));
            g.setColor(yColor);
            g.drawLine(Math.round(Point.point3D(new Vector3(gridStart, i, 0), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(gridStart, i, 0), cam, w, h).getY()), Math.round(Point.point3D(new Vector3(gridStart, i, gridDepth), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(gridStart, i, gridDepth), cam, w, h).getY()));
            g.drawLine(Math.round(Point.point3D(new Vector3(gridEnd, i, 0), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(gridEnd, i, 0), cam, w, h).getY()), Math.round(Point.point3D(new Vector3(gridEnd, i, gridDepth), cam, w, h).getX()), Math.round(Point.point3D(new Vector3(gridEnd, i, gridDepth), cam, w, h).getY()));
        }
        //g.setColor(zColor);
        for(int i = 1; i <= Math.round((float) gridDepth / gridDist); i+= gridDist)
        {
            int planeSize = Math.abs(gridStart) + Math.abs(gridEnd);
            double panelAlpha = 255 - ((double) gridDist * 255 / ((double) gridDepth / gridDist) * ((double) i / gridDist));
            //System.out.println("A: " + panelAlpha);
            Color planeColor = new Color(zColor.getRed(), zColor.getGreen(), zColor.getBlue(), (int) Math.round(panelAlpha));
            g.setColor(planeColor);
            GameObject planeObj = new GameObject(new Plane(new Vector3(0, 0, (i * gridDist) + zOffset), new Vector3(planeSize, planeSize, 1)), Type.Null);
            drawWirePlane(g, planeObj, cam);
        }
    }
}
