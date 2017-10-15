package kr.co.landvibe.handicraft.data.domain;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Furniture implements Serializable {

    public static final String KEY = "funiture";
    private static final long serialVersionUID = 1049314199876487430L;
    @SerializedName("fid")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("state")
    private String state; // 판매 or 공유
    @SerializedName("grade")
    private String grade; // 제품 상태 (A,B,C,F)
    @SerializedName("description")
    private String description;
    @SerializedName("images")
    private List<String> images;
    @SerializedName("type")
    private String type; // 가구 타입(의자, 쇼파, 책장, ...)
    @SerializedName("brand")
    private String brand; // 브랜드
    @SerializedName("periodOfUse")
    private String periodOfUse; // 사용 기간
    @SerializedName("price")
    private long price; // 판매가( 공유시 0원)
    @SerializedName("width")
    private double width; // 가로, x
    @SerializedName("length")
    private double length; // 세로, y
    @SerializedName("height")
    private double height; // 높이, z
    @SerializedName("location")
    private String location; // 거래지역
    @SerializedName("createAt")
    private Calendar createAt; // 업로드 날짜
    @SerializedName("updateAt")
    private Calendar updateAt; // 업로드 날짜
    @SerializedName("closed")
    private boolean closed;

    public Furniture() {
    }

    public Furniture(long id, String title, String state, String grade, String description, List<String> images, String type, String brand, String periodOfUse, long price, double width, double length, double height, String location, Calendar createAt, Calendar updateAt, boolean closed) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.grade = grade;
        this.description = description;
        this.images = images;
        this.type = type;
        this.brand = brand;
        this.periodOfUse = periodOfUse;
        this.price = price;
        this.width = width;
        this.length = length;
        this.height = height;
        this.location = location;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.closed = closed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPeriodOfUse() {
        return periodOfUse;
    }

    public void setPeriodOfUse(String periodOfUse) {
        this.periodOfUse = periodOfUse;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Calendar getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Calendar createAt) {
        this.createAt = createAt;
    }

    public Calendar getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Calendar updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
