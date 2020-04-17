package Drinks;

public class Espresso implements Drink {
    @Override
    public String getName() {
        return "Espresso";
    }

    @Override
    public float getPrice() {
        return 1.5f;
    }

    @Override
    public int getNeededCoffeePowder() {
        return 10;
    }

    @Override
    public int getNeededMilkPowder() {
        return 0;
    }

    @Override
    public int getNeededWater() {
        return 60;
    }

    @Override
    public int getNeededSugar() {
        return 0;
    }
}
