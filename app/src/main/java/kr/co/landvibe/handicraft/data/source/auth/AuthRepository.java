package kr.co.landvibe.handicraft.data.source.auth;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.GlobalApp;
import kr.co.landvibe.handicraft.data.domain.NaverUser;
import kr.co.landvibe.handicraft.data.source.auth.remote.AuthService;
import kr.co.landvibe.handicraft.data.source.auth.remote.NaverAuthService;
import kr.co.landvibe.handicraft.data.support.NaverResponseWrapper;
import kr.co.landvibe.handicraft.error.ForbiddenException;
import kr.co.landvibe.handicraft.error.InternalServerException;
import kr.co.landvibe.handicraft.error.NotFoundException;
import kr.co.landvibe.handicraft.error.UnAuthorizationException;
import kr.co.landvibe.handicraft.utils.LogUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static kr.co.landvibe.handicraft.utils.DefineUtils.CRAFT_TOKEN_KEY;
import static kr.co.landvibe.handicraft.utils.DefineUtils.NAVER_HOST_URL;
import static kr.co.landvibe.handicraft.utils.RegexUtils.tokenFilter;


public class AuthRepository implements AuthDataSource {

    @Nullable
    private static AuthRepository INSTANCE = null;

    private AuthService mAuthService;

    private NaverAuthService mNaverAuthService;

    private AuthRepository() {
        OkHttpClient okHttpClient = GlobalApp.getOkHttpClientInstance();

        Retrofit retrofit = GlobalApp.getRetrofitInstance(okHttpClient);

        Retrofit naverRetrofit = new Retrofit.Builder()
                .baseUrl(NAVER_HOST_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mAuthService = retrofit.create(AuthService.class);

        mNaverAuthService = naverRetrofit.create(NaverAuthService.class);
    }

    public static AuthRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthRepository();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        mAuthService = null;
        mNaverAuthService = null;
        INSTANCE = null;
    }

    @Override
    public Maybe<String> createAuth(@NonNull String accessToken, @NonNull NaverUser naverUser) {
        return mAuthService.signUp(
                accessToken,
                naverUser.getId(),
                naverUser.getName(),
                naverUser.getNickname(),
                "","",
                naverUser.getBirthday(),
                naverUser.getProfile_image()
        ).flatMap(response -> {
            if (response.isSuccessful()) {
                LogUtils.d("Success to create auth");
                LogUtils.d("Response Code : " + response.code());

                String craftToken = response.headers().get(CRAFT_TOKEN_KEY);
                return Maybe.just(tokenFilter(craftToken));
            } else {
                switch (response.code()) {
                    case 401:
                        return Maybe.error(new UnAuthorizationException());
                    case 500:
                        return Maybe.error(new InternalServerException());
                    default:
                        return Maybe.empty();
                }
            }
        });
    }

    @Override
    public Maybe<String> getAuth(@NonNull String accessToken) {
        return mAuthService.signin(accessToken)
                .flatMap(response -> {
                    if (response.isSuccessful()) {
                        LogUtils.d("Success to get auth");
                        LogUtils.d("Response Code : " + response.code());

                        String craftToken = response.headers().get(CRAFT_TOKEN_KEY);
                        return Maybe.just(tokenFilter(craftToken));
                    } else {
                        switch (response.code()) {
                            case 401:
                                return Maybe.error(new UnAuthorizationException());
                            case 500:
                                return Maybe.error(new InternalServerException());
                            default:
                                return Maybe.empty();
                        }
                    }
                });
    }

    @Override
    public Maybe<NaverUser> getNaverUser(@NonNull String accessToken, @NonNull String tokenType) {
        return mNaverAuthService.getUserInfo(tokenType + " " + accessToken)
                .flatMap(response -> {
                    if (response.isSuccessful()) {
                        NaverResponseWrapper<NaverUser> body = response.body();
                        NaverUser naverUser;
                        try {
                            naverUser = body.getResponse();
                        } catch (NullPointerException e) {
                            LogUtils.e("Response Message : " + body.getMessage());
                            LogUtils.e("Response ResultCode : " + body.getResultcode());
                            return Maybe.error(new NotFoundException());
                        }
                        return Maybe.just(naverUser);
                    } else {
                        switch (response.code()) {
                            case 401:
                                return Maybe.error(new UnAuthorizationException());
                            case 403:
                                return Maybe.error(new ForbiddenException());
                            case 404:
                                return Maybe.error(new NotFoundException());
                            case 500:
                                return Maybe.error(new InternalServerException());
                            default:
                                return Maybe.empty();
                        }
                    }
                });
    }

}
