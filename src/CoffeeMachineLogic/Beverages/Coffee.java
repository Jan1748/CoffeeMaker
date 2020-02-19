package CoffeeMachineLogic.Beverages;

import CoffeeMachineLogic.Stock.Recource;
import CoffeeMachineLogic.Stock.StockEnum;

import java.util.HashMap;

public class Coffee {
    private HashMap<StockEnum, Integer> neededRecources;

    public Coffee(){
        neededRecources = new HashMap<>();
        neededRecources.put(StockEnum.COFFEEPOWDER, 15);
    }
}
