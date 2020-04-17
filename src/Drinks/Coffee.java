package Drinks;

public class Coffee implements Drink {
    @Override
    public String getName() {
        return "Coffee";
    }

    @Override
    public float getPrice() {
        return 2.0f;
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
        return 250;
    }

    @Override
    public int getNeededSugar() {
        return 0;
    }
}
