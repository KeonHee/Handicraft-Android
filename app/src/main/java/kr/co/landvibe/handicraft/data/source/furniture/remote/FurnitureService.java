package kr.co.landvibe.handicraft.data.source.furniture.remote;


import io.reactivex.Single;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import kr.co.landvibe.handicraft.data.support.Pagination;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FurnitureService {

    @Headers("User-Agent: Android")
    @FormUrlEncoded
    @POST("/furniture")
    Single<Furniture> createFurniture(
            @Header("Authorization") String authorization,
            @Body Furniture furniture
    );

    @Headers("User-Agent: Android")
    @GET("/furniture/{id}")
    Single<Furniture> getFurniture(
            @Header("Authorization") String authorization,
            @Path("id") long id
    );

    @Headers("User-Agent: Android")
    @GET("/furniture")
    Single<Pagination<Furniture>> getFurnitureList(
            @Header("Authorization") String authorization,
            @Query("page") int page,
            @Query("per_page") int perPage
    );

    @Headers("User-Agent: Android")
    @FormUrlEncoded
    @PUT("/furniture/{id}")
    Single<Furniture> updateFurniture(
            @Header("Authorization") String authorization,
            @Path("id") long id,
            @Body Furniture furniture
    );

    @Headers("User-Agent: Android")
    @GET("/furniture/{id}")
    Single<Response> deleteFurniture(
            @Header("Authorization") String authorization,
            @Path("id") long id
    );

}
