public class Machine {
    private Assortment assortment;
    private Stock stock;
    private CashBox cashBox;

    private Composition composition;

    public void setAssortment(Assortment assortment) {
        this.assortment = assortment;
        this.composition = new Composition(assortment.getDrinks()[0]);
    }

    public Assortment getAssortment() {
        return this.assortment;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public void setCashBox(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    public CashBox getCashBox() {
        return cashBox;
    }

    public Composition getComposition() {
        return composition;
    }
}
