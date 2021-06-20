package ru.nsu.spirin.restaurant;

public class Restaurant {
    private final Waiter waiter;
    private final Chef chef;
    public final Meal activeMeal;

    public Restaurant(int numOfMeals) {
        this.activeMeal = new Meal();
        waiter = new Waiter(this, numOfMeals);
        chef = new Chef(this, numOfMeals);
    }

    public void startWorking() {
        System.out.println("Restaurant started work.");
        waiter.start();
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        chef.start();

        try {
            waiter.join();
            chef.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Restaurant finished work.");
    }
}
