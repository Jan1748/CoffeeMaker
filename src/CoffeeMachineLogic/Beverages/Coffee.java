package CoffeeMachineLogic.Beverages;

import CoffeeMachineLogic.Stock.StockEnum;

import java.util.HashMap;

public class Coffee implements Beverage {
    private HashMap<StockEnum, Integer> neededResources;
    private float price = 2f;

    public Coffee(){
        neededResources = new HashMap<>();
        neededResources.put(StockEnum.COFFEEPOWDER, 10);
        neededResources.put(StockEnum.WATER, 250);
    }

    @Override
    public HashMap<StockEnum, Integer> getNeededResources() {
        return neededResources;
    }

    @Override
    public float getPrice() {
        return price;
    }
}
