package CoffeeResources;

public class CoffeePowder implements Resource {
    private int coffeePowderStock = 1000;

    @Override
    public int getRecourceFillingLevel() {
        return coffeePowderStock;
    }

    @Override
    public void fillRecources() {
        coffeePowderStock = 1000;
    }

    @Override
    public void useRecources(int use) {
        coffeePowderStock -= use;
    }
}
