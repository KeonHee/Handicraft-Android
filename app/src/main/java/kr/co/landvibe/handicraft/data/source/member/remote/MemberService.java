package kr.co.landvibe.handicraft.data.source.member.remote;


import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.data.domain.Member;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MemberService {

    @Headers("User-Agent: Android")
    @GET("/users/{uid}")
    Maybe<Response<Member>> getMember(
            @Header("Authorization") String token,
            @Path("uid") String uid
    );

    @Headers("User-Agent: Android")
    @FormUrlEncoded
    @PUT("/users/{uid}")
    Maybe<Response<Member>> updateMember(
            @Header("Authorization") String token,
            @Path("uid") String uid,
            @Body Member user
    );

    @Headers("User-Agent: Android")
    @DELETE("/users/{uid}")
    Maybe<Response<String>> deletetMember(
            @Header("Authorization") String token,
            @Path("uid") String uid
    );

}
