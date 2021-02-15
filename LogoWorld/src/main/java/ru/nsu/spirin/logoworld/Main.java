package ru.nsu.spirin.logoworld;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <program> <file with program>\n");
            return;
        }

        try {
            LogoWorld logoWorld = new LogoWorld(args[0]);
            logoWorld.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
