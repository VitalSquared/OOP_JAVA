package ru.nsu.spirin.logoworld;

import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("Startup...");
        try {
            String program = null;
            boolean useSwing = false;

            if (args.length >= 1) {
                program = args[0];
            }
            if (args.length >= 2) {
                useSwing = args[1].equals("--swing");
            }

            LogoWorld logoWorld = new LogoWorld(program, useSwing);
            logoWorld.run();
        }
        catch (Exception e) {
            logger.error("Exception: " + e.getLocalizedMessage() + "!");
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
        logger.debug("Finishing...");
    }
}
