package Drinks;

public class Cappuccino implements Drink {
    @Override
    public String getName() {
        return "Cappuccino";
    }

    @Override
    public float getPrice() {
        return 2.5f;
    }

    @Override
    public int getNeededCoffeePowder() {
        return 20;
    }

    @Override
    public int getNeededMilkPowder() {
        return 10;
    }

    @Override
    public int getNeededWater() {
        return 180;
    }

    @Override
    public int getNeededSugar() {
        return 0;
    }
}
