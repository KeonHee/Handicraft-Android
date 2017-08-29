package kr.co.landvibe.handicraft.auth;


import android.content.Context;

import com.nhn.android.naverlogin.OAuthLogin;

public interface SignInContract {

    interface View {

        Context getContext();

        void showLoading();

        void hideLoading();

        void moveToMainActivity();

    }

    interface Presenter {

        void attachView(SignInContract.View view);

        void detachView();

        void login(String accessToken, String refreshToken, long expiresAt, String tokenType);

        void checkSessionNaverOauth(OAuthLogin mOAuthLoginInstance);

    }

}