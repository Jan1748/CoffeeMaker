package CoffeeMachineLogic.Beverages;

public class Beverages {
    private Coffee coffee = new Coffee();

    public Beverage getBeverage(BeveragesEnum beveragesEnum) {
       switch (beveragesEnum){
           case COFFEE: return coffee;
       }
       return null;
    }
}
