package kr.co.landvibe.handicraft.type;


import java.util.ArrayList;
import java.util.List;

public enum StateType {
    NEW("새것"), NEW_FAULT("새것+하자있음"), GOOD("거의 새것"), USED("중고"), USED_FAULT("중고+하자있음"), EMPTY("");

    private String text;

    StateType(String text) {
        this.text = text;
    }

    public static StateType get(int idx) {
        switch (idx) {
            case 0:
                return NEW;
            case 1:
                return NEW_FAULT;
            case 2:
                return GOOD;
            case 3:
                return USED;
            case 4:
                return USED_FAULT;
            default:
                return EMPTY;
        }
    }

    public static String[] names(){
        List<String> results = new ArrayList<>();
        for (StateType s : StateType.values()){
            results.add(s.getText());
        }
        return results.toArray(new String[results.size()]);
    }

    public String getText() {
        return text;
    }
}
