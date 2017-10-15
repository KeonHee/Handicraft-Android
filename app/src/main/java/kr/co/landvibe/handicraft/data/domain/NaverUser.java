package kr.co.landvibe.handicraft.data.domain;


import java.io.Serializable;

public class NaverUser implements Serializable {
    private static final long serialVersionUID = -1390786679668130476L;

    private String id;
    private String name;
    private String nickname;
    private String email;
    private String profile_image;
    private String birthday;

    public NaverUser() {
    }

    public NaverUser(String id, String name, String nickname, String email, String profile_image, String birthday) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.profile_image = profile_image;
        this.birthday = birthday;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
