package com.mk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame
{
    public Main()
    {
        initUI();
    }

    private void initUI()
    {

        Display d = new Display(1000, 500);
        add(d);

        setTitle("SpaceWar 3D");

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new TAdapter());
        GameManager gm = new GameManager(d);
        gm.start();
        //add(gm);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }

    private static class TAdapter extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent e) { GameManager.keyReleased(e); }

        @Override
        public void keyPressed(KeyEvent e) { GameManager.keyPressed(e); }
    }
}
