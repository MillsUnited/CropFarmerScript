package com.mills;

import com.github.kwhat.jnativehook.GlobalScreen;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        System.setProperty("jnativehook.lib.path", System.getProperty("java.io.tmpdir"));

        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        CropFarmerGUI gui = new CropFarmerGUI();
        gui.createGUI();
    }
}