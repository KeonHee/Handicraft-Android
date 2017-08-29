package kr.co.landvibe.handicraft.type;


import java.util.ArrayList;
import java.util.List;

public enum TradeType {
    SELL("팝니다"), SHARE("가져가세요"), EMPTY("");

    private String text;

    TradeType(String text) {
        this.text = text;
    }

    public static TradeType get(int idx) {
        switch (idx) {
            case 0:
                return SELL;
            case 1:
                return SHARE;
            default:
                return EMPTY;
        }
    }

    public static String[] names(){
        List<String> results = new ArrayList<>();
        for (TradeType t : TradeType.values()){
            results.add(t.getText());
        }
        return results.toArray(new String[results.size()]);
    }

    public String getText() {
        return text;
    }
}
