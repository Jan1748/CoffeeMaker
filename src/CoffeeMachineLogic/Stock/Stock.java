package CoffeeMachineLogic.Stock;

import java.util.HashMap;
import java.util.Set;

public class Stock {
    private CoffeePowder coffeePowder = new CoffeePowder();
    private MilkPowder milkPowder = new MilkPowder();
    private SugarCubes sugarCubes = new SugarCubes();
    private Water water = new Water();

    public int getFillLevel(StockEnum stockEnum){
        Recourse currentRecourse = getRecourse(stockEnum);
        return currentRecourse.getFillLevel();
    }
    public void useRecourse(StockEnum stockEnum, int quantity){
        Recourse currentRecourse = getRecourse(stockEnum);
        currentRecourse.take(quantity);
    }
    public void outputStocksWithFillLevel(){
        System.out.println("Recourse:");
        System.out.println("Coffee Powder " + coffeePowder.getFillLevel() + "g");
        System.out.println("Milk Powder " + milkPowder.getFillLevel()+ "g");
        System.out.println("Sugar Cubes " + sugarCubes.getFillLevel()+ " pieces");
        System.out.println("Water " + water.getFillLevel()+ " ml");

    }
    public void useResources(HashMap<StockEnum, Integer> resources) {
        Set<StockEnum> resourceNames = resources.keySet();
        for (StockEnum s : resourceNames) {
            if (s == StockEnum.SUGARCUBES) {
                System.out.println("Use Recourse: " + s + " " + resources.get(s) + " pieces");
            } else if (s == StockEnum.WATER){
                System.out.println("Use Recourse: " + s + " " + resources.get(s) + " ml");
            } else {
                System.out.println("Use Recourse: " + s + " " + resources.get(s) + "g");

            }
            switch (s) {
                case COFFEEPOWDER:
                    useRecourse(s, resources.get(s));
                    break;
                case MILKPOWDER:
                    useRecourse(s, resources.get(s));
                    break;
                case SUGARCUBES:
                    useRecourse(s, resources.get(s));
                    break;
                case WATER:useRecourse(s, resources.get(s));
            }
        }
    }
    private Recourse getRecourse(StockEnum stockEnum){
        switch (stockEnum){
            case COFFEEPOWDER: return coffeePowder;
            case MILKPOWDER: return milkPowder;
            case SUGARCUBES: return sugarCubes;
            case WATER: return water;
        }
        return null;
    }
}
