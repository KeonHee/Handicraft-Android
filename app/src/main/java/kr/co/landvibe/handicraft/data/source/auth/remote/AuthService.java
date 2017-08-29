package kr.co.landvibe.handicraft.data.source.auth.remote;

import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.data.domain.Member;
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
            @Field("accessToken") String accessToken,
            @Field("userInfo") Member member
    );

    @Headers("User-Agent: Android")
    @FormUrlEncoded
    @POST("/auth/login")
    Maybe<Response<String>> login(
            @Field("access_token") String accessToken
    );

}
