package ru.nsu.spirin.logoworld;

import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Startup...");
        try {
            LogoWorld logoWorld = new LogoWorld(args.length >= 1 ? args[0] : null, args.length == 2 && args[1].equals("--swing"));
            logoWorld.run();
        }
        catch (Exception e) {
            logger.error("Exception: " + e.getLocalizedMessage() + "!");
            e.printStackTrace();
        }
        logger.debug("Finishing...");
    }
}
