package com.mills;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CropFarmerGUI {

    private CropFarmerManager manager;

    private JFrame frame;
    private JTextField alternatingTimeTextBox;
    private JTextField stoppedTimeTextBox;
    private JLabel pressKeybindLabel;
    private JButton keybindButton;

    private int keyCode = -1;
    private boolean keybindWindowOpen = false;

    public CropFarmerGUI() {
        frame = new JFrame("Crop Farmer GUI");
        frame.setSize(600, 300);
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

        alternatingTimeTextBox = new JTextField();
        alternatingTimeTextBox.setBounds(115, 90, 100, 20);
        alternatingTimeTextBox.setFont(new Font("Arial", Font.PLAIN, 16));
        alternatingTimeTextBox.setBackground(Color.BLACK);
        alternatingTimeTextBox.setForeground(Color.WHITE);

        JLabel alternatingTimeLabel = new JLabel("Alt. time:");
        alternatingTimeLabel.setBounds(10, 90, 100, 20);
        alternatingTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        alternatingTimeLabel.setForeground(Color.WHITE);

        stoppedTimeTextBox = new JTextField();
        stoppedTimeTextBox.setBounds(115, 115, 100, 20);
        stoppedTimeTextBox.setFont(new Font("Arial", Font.PLAIN, 16));
        stoppedTimeTextBox.setBackground(Color.BLACK);
        stoppedTimeTextBox.setForeground(Color.WHITE);

        JLabel stoppedTimeLabel = new JLabel("Stopped time:");
        stoppedTimeLabel.setBounds(10, 115, 100, 20);
        stoppedTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        stoppedTimeLabel.setForeground(Color.WHITE);

        JLabel keybindLabel = new JLabel("Select Keybind:");
        keybindLabel.setBounds(10, 135, 250, 30);
        keybindLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        keybindLabel.setForeground(Color.WHITE);

        keybindButton = new JButton("Set Keybind");
        keybindButton.setBounds(10, 185, 135, 30);
        keybindButton.setFont(new Font("Arial", Font.PLAIN, 16));
        keybindButton.setForeground(Color.WHITE);
        keybindButton.setBackground(Color.BLACK);

        pressKeybindLabel = new JLabel("Current Keybind: None");
        pressKeybindLabel.setBounds(10, 163, 300, 15);
        pressKeybindLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pressKeybindLabel.setForeground(Color.WHITE);

        keybindButton.addActionListener(e -> openKeybindWindow());

        frame.add(titleLabel);
        frame.add(timeLabel);
        frame.add(alternatingTimeTextBox);
        frame.add(stoppedTimeTextBox);
        frame.add(alternatingTimeLabel);
        frame.add(stoppedTimeLabel);
        frame.add(keybindLabel);
        frame.add(keybindButton);
        frame.add(pressKeybindLabel);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void openKeybindWindow() {
        if (keybindWindowOpen) {
            JOptionPane.showMessageDialog(frame, "A keybind window is already open!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        keybindWindowOpen = true;
        JFrame keybindFrame = new JFrame("Press Key");
        keybindFrame.setSize(250, 100);
        keybindFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        keybindFrame.getContentPane().setBackground(Color.DARK_GRAY);
        keybindFrame.setLayout(new FlowLayout());

        JLabel keybindPromptLabel = new JLabel("Press any key:");
        keybindPromptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        keybindPromptLabel.setForeground(Color.white);

        keybindFrame.add(keybindPromptLabel);
        keybindFrame.setLocationRelativeTo(null);

        keybindFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyCode = e.getKeyCode();  // Store the keyCode here when the user presses a key
                pressKeybindLabel.setText("Current Keybind: " + KeyEvent.getKeyText(keyCode));  // Display the key name
                keybindFrame.dispose();
                keybindWindowOpen = false;

                registerGlobalKeybind(keyCode);  // Use the stored keyCode for keybind registration
            }
        });

        keybindFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                keybindWindowOpen = false;
            }
        });

        keybindFrame.setVisible(true);
        keybindFrame.setResizable(false);
        keybindFrame.requestFocusInWindow();
    }

    private void registerGlobalKeybind(int keyCode) {

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {

            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == keyCode) {

                SwingUtilities.invokeLater(() -> {
                    if (manager != null) {
                        manager.toggleFarming();
                    }
                });
                return true;
            }
            return false;
        });
    }

    public int getAlternatingTime() {
        try {
            return Integer.parseInt(alternatingTimeTextBox.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getStoppedTime() {
        try {
            return Integer.parseInt(stoppedTimeTextBox.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}