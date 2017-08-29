package kr.co.landvibe.handicraft.data.support;


import com.google.gson.annotations.SerializedName;

public class NaverResponseWrapper<T> {
    @SerializedName("resultcode")
    private int resultcode;
    @SerializedName("message")
    private String message;
    @SerializedName("response")
    private T response;

    public NaverResponseWrapper(int resultcode, String message, T response) {
        this.resultcode = resultcode;
        this.message = message;
        this.response = response;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
