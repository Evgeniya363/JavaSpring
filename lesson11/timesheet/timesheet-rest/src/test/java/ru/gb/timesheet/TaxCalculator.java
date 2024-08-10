package ru.gb.timesheet;

public class TaxCalculator {
    final TaxResolver taxResolver;

    public TaxCalculator(TaxResolver taxResolver) {
        this.taxResolver = taxResolver;
    }

    //Товар с учетом НДС
    public double getPriceWithTax(double price) {
        return price + price*taxResolver.getCurrentTax();
    }
}
