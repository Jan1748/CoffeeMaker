package Beverages;

import CoffeeResources.Resource;

import java.util.List;

public class LatteMacchiato implements Beverage {
    @Override
    public List<Resource> getListOfResources(CoffeSize size) {
        return null;
    }

    @Override
    public float getPrice(CoffeSize size) {
        return 0;
    }
}