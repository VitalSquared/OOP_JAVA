package ru.nsu.spirin.restaurant;

public class Chef extends Thread {
    private final Restaurant restaurant;
    private int numOfMealsLeft;

    public Chef(Restaurant restaurant, int numOfMealsLeft) {
        this.restaurant = restaurant;
        this.numOfMealsLeft = numOfMealsLeft;
    }

    public void cookMeal() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        numOfMealsLeft--;
        System.out.println("Meal cooked. " + numOfMealsLeft + " meals left");
    }

    @Override
    public void run() {
        synchronized (restaurant.activeMeal) {
            while (numOfMealsLeft > 0) {
                cookMeal();
                restaurant.activeMeal.notify();
                try {
                    restaurant.activeMeal.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
