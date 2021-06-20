package ru.nsu.spirin.restaurant;

public class Waiter extends Thread {
    private final Restaurant restaurant;
    private int numOfMealsLeft;

    public Waiter(Restaurant restaurant, int numOfMealsLeft) {
        this.restaurant = restaurant;
        this.numOfMealsLeft = numOfMealsLeft;
    }

    public void serveMeal() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        numOfMealsLeft--;
        System.out.println("Meal served");
    }

    @Override
    public void run() {
        while (numOfMealsLeft > 0) {
            synchronized (restaurant.activeMeal) {
                try {
                    restaurant.activeMeal.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                serveMeal();
                restaurant.activeMeal.notify();
            }
        }
    }
}