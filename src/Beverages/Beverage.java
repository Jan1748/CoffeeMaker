package Beverages;

import CoffeeResources.Resource;

import java.util.List;

public interface Beverage {
    List<Resource> getListOfResources(CoffeSize size);
    float getPrice(CoffeSize size);


}
