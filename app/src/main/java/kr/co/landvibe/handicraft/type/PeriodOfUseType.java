package kr.co.landvibe.handicraft.type;


import java.util.ArrayList;
import java.util.List;

public enum PeriodOfUseType {
    ONE_YEAR_BELOW("1년 이하"), ONE_YEAR("1년"), TWO_YEAR("2년"), THREE_YEAR("3년"), FOUR_YEAR("4년"), FIVE_YEAR_ABOVE("5년 이상"), EMPTY("");

    private String text;

    PeriodOfUseType(String text) {
        this.text = text;
    }

    public static PeriodOfUseType get(int idx) {
        switch (idx) {
            case 0:
                return ONE_YEAR_BELOW;
            case 1:
                return ONE_YEAR;
            case 2:
                return TWO_YEAR;
            case 3:
                return THREE_YEAR;
            case 4:
                return FOUR_YEAR;
            case 5:
                return FIVE_YEAR_ABOVE;
            default:
                return EMPTY;
        }
    }

    public static String[] names(){
        List<String> results = new ArrayList<>();
        for (PeriodOfUseType p : PeriodOfUseType.values()){
            results.add(p.getText());
        }
        return results.toArray(new String[results.size()]);
    }

    public String getText() {
        return text;
    }
}
