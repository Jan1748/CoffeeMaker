package Drinks;

public abstract class Drink {
    private String name;
    private float price;

    private int neededCoffeePowder = 0; // in gram
    private int neededMilkPowder = 0; // in gram
    private int neededWater = 0; // in milliliter
    private int neededSugar = 0; // in cubes

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getNeededCoffeePowder() {
        return neededCoffeePowder;
    }

    public int getNeededMilkPowder() {
        return neededMilkPowder;
    }

    public int getNeededWater() {
        return neededWater;
    }

    public int getNeededSugar() {
        return neededSugar;
    }
}
