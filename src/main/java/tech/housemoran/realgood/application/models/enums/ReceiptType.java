package tech.housemoran.realgood.application.models.enums;

import tech.housemoran.realgood.application.models.Receipt;

public enum ReceiptType {
    UTILITY, FOOD, CLOTHING, LOAN, MISC, SCHOOL_FEES, INSURANCE, TRANSPORTATION;

    public String toString() {
        switch(this) {
            case UTILITY: return "UTILITY";
            case FOOD: return "FOOD";
            case LOAN: return "LOAN";
            case MISC: return "MISC";
            case CLOTHING: return "CLOTHING";
            case SCHOOL_FEES: return "SCHOOL_FEES";
            case INSURANCE: return "INSURANCE";
            case TRANSPORTATION: return "TRANSPORTATION";
        }
        return null;
    }

    public static ReceiptType toValue(final String value) {
        if (value.equalsIgnoreCase(UTILITY.toString())) {
            return ReceiptType.UTILITY;
        } else if (value.equalsIgnoreCase(FOOD.toString())) {
            return ReceiptType.FOOD;
        } else if (value.equalsIgnoreCase(CLOTHING.toString())) {
            return ReceiptType.CLOTHING;
        } else if (value.equalsIgnoreCase(LOAN.toString())) {
            return ReceiptType.LOAN;
        } else if (value.equalsIgnoreCase(MISC.toString())) {
            return ReceiptType.MISC;
        } else if (value.equalsIgnoreCase(SCHOOL_FEES.toString())) {
            return ReceiptType.SCHOOL_FEES;
        } else if (value.equalsIgnoreCase(INSURANCE.toString())) {
            return ReceiptType.INSURANCE;
        } else if (value.equalsIgnoreCase(TRANSPORTATION.toString())) {
            return ReceiptType.TRANSPORTATION;
        } else {
            return null;
        }
    }
}