package com.mills;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CropFarmerGUI {

    private CropFarmerManager manager;

    private JFrame frame;
    private JTextField alternatingTimeTextBox;
    private JTextField stoppedTimeTextBox;
    private boolean hookRegistered = false;

    public CropFarmerGUI() {
        disableLogging();
        this.manager = new CropFarmerManager(this);
        registerGlobalKeybind();
    }

    public void createGUI() {

        frame = new JFrame("Crop Farmer GUI");
        frame.setSize(600, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Crop Farmer:");
        titleLabel.setBounds(10, 10, 200, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel timeLabel = new JLabel("Time(ms):");
        timeLabel.setBounds(10, 60, 150, 30);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        timeLabel.setForeground(Color.WHITE);

        alternatingTimeTextBox = new JTextField("1000");
        alternatingTimeTextBox.setBounds(115, 90, 100, 20);
        alternatingTimeTextBox.setFont(new Font("Arial", Font.PLAIN, 16));
        alternatingTimeTextBox.setBackground(Color.BLACK);
        alternatingTimeTextBox.setForeground(Color.WHITE);

        JLabel alternatingTimeLabel = new JLabel("Alt. time:");
        alternatingTimeLabel.setBounds(10, 90, 100, 20);
        alternatingTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        alternatingTimeLabel.setForeground(Color.WHITE);

        stoppedTimeTextBox = new JTextField("1000");
        stoppedTimeTextBox.setBounds(115, 115, 100, 20);
        stoppedTimeTextBox.setFont(new Font("Arial", Font.PLAIN, 16));
        stoppedTimeTextBox.setBackground(Color.BLACK);
        stoppedTimeTextBox.setForeground(Color.WHITE);

        JLabel stoppedTimeLabel = new JLabel("Stopped time:");
        stoppedTimeLabel.setBounds(10, 115, 100, 20);
        stoppedTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        stoppedTimeLabel.setForeground(Color.WHITE);

        JLabel keybindInfoLabel = new JLabel("Keybind: F9");
        keybindInfoLabel.setBounds(10, 140, 200, 30);
        keybindInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        keybindInfoLabel.setForeground(Color.WHITE);

        ImageIcon icon = new ImageIcon("src/main/java/com/mills/carrot.png");
        frame.setIconImage(icon.getImage());

        frame.add(titleLabel);
        frame.add(timeLabel);
        frame.add(alternatingTimeTextBox);
        frame.add(stoppedTimeTextBox);
        frame.add(alternatingTimeLabel);
        frame.add(stoppedTimeLabel);
        frame.add(keybindInfoLabel);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void registerGlobalKeybind() {
        try {
            if (hookRegistered) {
                GlobalScreen.unregisterNativeHook();
                hookRegistered = false;
            }
        } catch (NativeHookException ignored) {}

        try {
            GlobalScreen.registerNativeHook();
            hookRegistered = true;

            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyPressed(NativeKeyEvent e) {
                    if (e.getKeyCode() == NativeKeyEvent.VC_F9) {
                        manager.toggleFarming();
                    }
                }
                @Override
                public void nativeKeyReleased(NativeKeyEvent e) {}
                @Override
                public void nativeKeyTyped(NativeKeyEvent e) {}
            });
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    public int getAlternatingTime() {
        try {
            return Integer.parseInt(alternatingTimeTextBox.getText());
        } catch (NumberFormatException e) {
            return 1000;
        }
    }

    public int getStoppedTime() {
        try {
            return Integer.parseInt(stoppedTimeTextBox.getText());
        } catch (NumberFormatException e) {
            return 1000;
        }
    }

    private void disableLogging() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackageName());
        logger.setLevel(Level.OFF);
    }
}