package ru.kuznetsov.myCustomException;

public class AddMoneyExceptoin extends Exception {

    double countOfMoney;

    public AddMoneyExceptoin(String message, double countOfMoney){
        super(message);
        this.countOfMoney = countOfMoney;
    }

    public double getCountOfMoney() {
        return countOfMoney;
    }
}
