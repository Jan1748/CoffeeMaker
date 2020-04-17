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
        return true;
    }

    public void consume(Composition composition) {}

    public void refill(int coffeePowder, int milkPowder, int sugarCubes) {
        this.coffeePowder += coffeePowder;
        this.milkPowder += milkPowder;
        this.sugarCubes += sugarCubes;
    }
}
