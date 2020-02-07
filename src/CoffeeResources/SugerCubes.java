package CoffeeResources;

public class SugerCubes implements Resource{
    private int sugarCubesStock = 100;
    @Override
    public int getRecourceFillingLevel() {
        return sugarCubesStock;
    }

    @Override
    public void fillRecources() {
        sugarCubesStock = 100;
    }

    @Override
    public void useRecources(int use) {
        sugarCubesStock -= use;
    }
}
