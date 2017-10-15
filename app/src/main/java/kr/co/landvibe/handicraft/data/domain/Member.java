package kr.co.landvibe.handicraft.data.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

public class Member extends RealmObject implements Serializable {

    private static final long serialVersionUID = 4909606623101545491L;

    @SerializedName("uid")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("phone")
    private String phone;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("address")
    private String address;
    @SerializedName("joinAt")
    private Date joinAt;
    @SerializedName("avatar")
    private String avatar;

    public Member() {
    }

    public Member(String id, String name, String nickname, String phone,
                  String birthday, String address, Date joinAt, String avatar) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.joinAt = joinAt;
        this.avatar = avatar;
    }

    public void bind(Member m) {
        // no id
        this.name = m.getName();
        this.nickname = m.getNickname();
        this.phone = m.getPhone();
        this.birthday = m.getBirthday();
        this.address = m.getAddress();
        this.joinAt = m.getJoinAt();
        this.avatar = m.getAvatar();
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
