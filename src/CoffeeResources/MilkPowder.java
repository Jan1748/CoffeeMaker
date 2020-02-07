package CoffeeResources;

public class MilkPowder implements Resource {
    private int milkPowderStock = 1000;
    @Override
    public int getRecourceFillingLevel() {
        return milkPowderStock;
    }

    @Override
    public void fillRecources() {
        milkPowderStock = 1000;
    }

    @Override
    public void useRecources(int use) {
        milkPowderStock -= use;
    }
}
