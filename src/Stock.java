import Drinks.Drink;

public class Stock {
    private int coffeePowder;
    private int milkPowder;
    private int sugarCubes;

    public Stock(int coffeePowder, int milkPowder, int sugarCubes) {
        this.coffeePowder = coffeePowder;
        this.milkPowder = milkPowder;
        this.sugarCubes = sugarCubes;
    }

    public boolean canConsume(Composition composition) {
        if (composition.getTotalCoffee() > this.coffeePowder) return false;
        if (composition.getTotalMilk() > this.milkPowder) return false;
        if (composition.getTotalSugar() > this.sugarCubes) return false;
        return true;
    }

    public void consume(Composition composition) {
        this.coffeePowder -= composition.getTotalCoffee();
        this.milkPowder -= composition.getTotalMilk();
        this.sugarCubes -= composition.getTotalSugar();
    }

    public void refill(int coffeePowder, int milkPowder, int sugarCubes) {
        this.coffeePowder += coffeePowder;
        this.milkPowder += milkPowder;
        this.sugarCubes += sugarCubes;
    }
}
