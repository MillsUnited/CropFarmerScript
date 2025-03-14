package com.mills;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class CropFarmerManager {

    private boolean isFarming = false;
    private final CropFarmerGUI gui;
    private Robot robot;
    private Thread farmingThread;

    public CropFarmerManager(CropFarmerGUI gui) {
        this.gui = gui;
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleFarming() {
        if (isFarming) {
            stopFarming();
        } else {
            startFarming();
        }
    }

    private void startFarming() {
        isFarming = true;

        farmingThread = new Thread(() -> {
            try {
                while (isFarming) {
                    int alternatingTime = gui.getAlternatingTime();
                    int stoppedTime = gui.getStoppedTime();

                    if (alternatingTime <= 0 || stoppedTime < 0) {
                        stopFarming();
                        return;
                    }

                    pressAndHoldKey(KeyEvent.VK_D, alternatingTime);
                    if (!isFarming) break;
                    sleep(stoppedTime);

                    pressAndHoldKey(KeyEvent.VK_A, alternatingTime);
                    if (!isFarming) break;
                    sleep(stoppedTime);
                }
            } catch (Exception e) {
                if (!(e instanceof InterruptedException)) {
                    e.printStackTrace();
                }
            }
        });

        farmingThread.start();
    }

    private void stopFarming() {
        isFarming = false;

        if (farmingThread != null) {
            farmingThread.interrupt();
        }

        try {
            robot.keyRelease(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_A);
        } catch (Exception ignored) {}
    }

    private void pressAndHoldKey(int keyCode, int duration) {
        if (!isFarming) return;

        try {
            robot.keyPress(keyCode);
            Thread.sleep(duration);
            robot.keyRelease(keyCode);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
