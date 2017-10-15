package kr.co.landvibe.handicraft.data.source.auth.remote;

import io.reactivex.Maybe;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @Headers("User-Agent: Android")
    @FormUrlEncoded
    @POST("/auth/signup")
    Maybe<Response<String>> signUp(
            @Field("access_token") String accessToken,
            @Field("uid") String uid,
            @Field("name") String name,
            @Field("nickname") String nickname,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("birthday") String birthday,
            @Field("avatar") String avatar
            );

    @Headers("User-Agent: Android")
    @FormUrlEncoded
    @POST("/auth/signin")
    Maybe<Response<String>> signin(
            @Field("access_token") String accessToken
    );

}