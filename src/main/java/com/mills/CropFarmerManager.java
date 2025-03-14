package com.mills;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CropFarmerManager {

    private CropFarmerGUI gui;
    private Robot robot;
    private volatile boolean running = false;
    private int keyCode;

    public CropFarmerManager(CropFarmerGUI gui) {
        this.gui = gui;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.keyCode = -1;
    }

    public void toggleFarming() {
        if (running) {
            // Stop farming
            running = false;
        } else {
            // Start farming
            running = true;
            startFarming();
        }
    }

    private void startFarming() {
        new Thread(() -> {
            while (running) {
                int alternatingTime = gui.getAlternatingTime();
                int stoppedTime = gui.getStoppedTime();

                if (alternatingTime <= 0 || stoppedTime < 0) {
                    running = false;
                    return;
                }

                pressAndHoldKey(KeyEvent.VK_D, alternatingTime);
                sleep(stoppedTime);

                if (!running) break;

                pressAndHoldKey(KeyEvent.VK_A, alternatingTime);
                sleep(stoppedTime);
            }
        }).start();
    }

    private void pressAndHoldKey(int keyCode, int duration) {
        if (!running) return;

        robot.keyPress(keyCode);
        sleep(duration);
        robot.keyRelease(keyCode);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
