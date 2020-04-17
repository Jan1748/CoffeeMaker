package Drinks;

public class MilkCoffee implements Drink {
    @Override
    public String getName() {
        return "Milk Coffee";
    }

    @Override
    public float getPrice() {
        return 2.7f;
    }

    @Override
    public int getNeededCoffeePowder() {
        return 20;
    }

    @Override
    public int getNeededMilkPowder() {
        return 20;
    }

    @Override
    public int getNeededWater() {
        return 350;
    }

    @Override
    public int getNeededSugar() {
        return 0;
    }
}
