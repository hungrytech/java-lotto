package lotto.domain;

import lotto.exception.EmptySourceException;
import lotto.exception.InvalidUnitException;

import java.util.Objects;

public class Price {
    private static final int BASIC_UNIT = 1000;
    private static final int CORRECT_UNIT = 0;

    private final int price;

    private Price(int price) {
        validateUnit(price);

        this.price = price;
    }

    public static Price of(String price) {
        validateEmpty(price);

        return new Price(Integer.parseInt(price));
    }

    public int getNumberOfTickets() {
        return price / BASIC_UNIT;
    }

    private static void validateEmpty(String price) {
        if (Objects.isNull(price) || price.isEmpty()) {
            throw new EmptySourceException();
        }
    }

    private static void validateUnit(int price) {
        if (!isCorrectUnit(price)) {
            throw new InvalidUnitException();
        }
    }

    private static boolean isCorrectUnit(int price) {
        return divideBasicUnit(price) == CORRECT_UNIT;
    }

    private static int divideBasicUnit(int price) {
        return price % BASIC_UNIT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return price == price1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
