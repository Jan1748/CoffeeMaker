package CoffeeMachineLogic.Beverages;

import CoffeeMachineLogic.Stock.StockEnum;

import java.util.HashMap;

public interface Beverage {
    HashMap<StockEnum, Integer> getNeededResources();
    float getPrice();

}
