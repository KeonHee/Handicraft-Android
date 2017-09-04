package kr.co.landvibe.handicraft.data.domain;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import kr.co.landvibe.handicraft.type.GenderType;

public class Member extends RealmObject implements Serializable {

    private static final long serialVersionUID = 4909606623101545491L;

    private String id;
    private String name;
    private String phone;
    private GenderType gender;
    private String address;
    private String feature;
    private Date joinAt;
    private String craftToken;
    private String profileImage;

    public Member() {
    }

    public Member(String id, String name, String phone, GenderType gender, String address, String feature, Date joinAt, String craftToken, String profileImage) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.feature = feature;
        this.joinAt = joinAt;
        this.craftToken = craftToken;
        this.profileImage = profileImage;
    }

    public void bind(Member m) {
        // no id
        this.name = m.getName();
        this.phone = m.getPhone();
        this.gender = m.getGender();
        this.address = m.getAddress();
        this.feature = m.getFeature();
        this.joinAt = m.getJoinAt();
        this.craftToken = m.getCraftToken();
        this.profileImage = m.getProfileImage();
    }

    public void setGender(String gender) {
        switch (gender) {
            case "F":
                this.gender = GenderType.FEMALE;
                break;
            case "M":
                this.gender = GenderType.MALE;
                break;
            default:
                this.gender = GenderType.UNKNOWN;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public String getCraftToken() {
        return craftToken;
    }

    public void setCraftToken(String craftToken) {
        this.craftToken = craftToken;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
