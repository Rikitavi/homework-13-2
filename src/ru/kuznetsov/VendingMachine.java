package ru.kuznetsov;


import ru.kuznetsov.drinks.ColdDrinkType;
import ru.kuznetsov.drinks.DrinkType;
import ru.kuznetsov.drinks.HotDrinkType;
import ru.kuznetsov.drinks.Product;
import ru.kuznetsov.myCustomException.NotEnoughMoneyException;
import ru.kuznetsov.myCustomException.NotValidKeyException;
import ru.kuznetsov.myCustomException.AddMoneyExceptoin;

import java.util.Random;

/**
 * Торговый автомат
 */
public class VendingMachine {
    private double money = 0;
    private Product[] drinks = new Product[]{
            new Product(ColdDrinkType.COCA, 10),
            new Product(ColdDrinkType.PEPSI, 10),
            new Product(ColdDrinkType.SPRITE, 1),
            new Product(ColdDrinkType.FANTA, 3),
            new Product(HotDrinkType.GREEN_TEA, 100),
            new Product(HotDrinkType.BLACK_TEA, 100),
            new Product(HotDrinkType.AMERICANO, 100),
            new Product(HotDrinkType.CAPPUCINO, 100)
    };

    /**
     * @return было ли замятие купюры
     */
    public boolean isSuccessful() {
        Random random = new Random();
        return random.nextBoolean();
    }

    /**
     * Добавление купюр в автомат
     *
     * @param money - сумма внесенных купюр
     * @return текущий остаток
     */
    public double addMoney(double money) throws AddMoneyExceptoin {
        if (!isSuccessful()) {
            this.money += money;
            return this.money;
        } else {
            throw new AddMoneyExceptoin("Произошло замятие купюры!", money);
        }
    }

    /**
     * Получает из автомата выбранный напиток
     * <p>
     * В методе есть проверка корректности кода напитка, проверка остатков данного продукта
     * и достаточно ли денег чтобы его купить. В случае ошибок выбрасывать соответствующее исключение
     *
     * @param key код продукта
     * @return напиток;
     */
    public DrinkType giveMeADrink(int key) throws NotValidKeyException, NotEnoughMoneyException {
        if (!isKeyValid(key)) {
            throw new NotValidKeyException("Введите другое число. Под данным номером товар не существует!");
        }

        Product selected = drinks[key];
        DrinkType drink = selected.take();
        double price = 0;
        if (drink != null) {
            price = drink.getPrice();
        }
        if (!isMoneyEnough(selected)) {
            throw new NotEnoughMoneyException("Для совершения покупки не хватает " + Math.abs(price - money) + "рублей!");
        }
        money -= price;
        return drink;
    }

    /**
     * Формирует список товаров
     *
     * @return строки с информацией о товаре
     */
    public String[] getDrinkTypes() {
        String[] result = new String[drinks.length];
        for (int i = 0; i < drinks.length; i++) {
            result[i] = String.format("%d - %12s: %6.2f руб", i, drinks[i].getName(), drinks[i].getPrice());
        }
        return result;
    }

    /**
     * Проверка что хватает денег на выбранный напиток
     *
     * @param selected - выбранный напиток
     * @return true если денег хватает, иначе - false
     */
    private boolean isMoneyEnough(Product selected) {
        return money >= selected.getPrice();
    }

    /**
     * Проверка что выбрали существующий тип напитка
     * Не проверяет остаток, только границы массива
     *
     * @param key номер напитка
     * @return true если есть напиток с таким номером, иначе false
     */
    private boolean isKeyValid(int key) {
        return key >= 0 && key < drinks.length;
    }

    /**
     * Выдать сдачу.
     * Количество введеных купюр обнуляется
     *
     * @return остаток
     */
    public double getChange() {
        double money = this.money;
        this.money = 0;
        return money;
    }
}
