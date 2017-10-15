package kr.co.landvibe.handicraft.data.source.furniture;


import io.reactivex.Single;
import kr.co.landvibe.handicraft.GlobalApp;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import kr.co.landvibe.handicraft.data.source.furniture.remote.FurnitureService;
import kr.co.landvibe.handicraft.data.support.Pagination;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class FurnitureRepository implements FurnitureDataSource {

    private static FurnitureRepository INSTANCE;

    private FurnitureService mFurnitureService;

    private FurnitureRepository() {
        OkHttpClient okHttpClient = GlobalApp.getOkHttpClientInstance();
        Retrofit retrofit = GlobalApp.getRetrofitInstance(okHttpClient);

        mFurnitureService = retrofit.create(FurnitureService.class);
    }

    public static FurnitureRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FurnitureRepository();
        }
        return INSTANCE;


    }

    public void destroyInstance() {
        INSTANCE = null;
        mFurnitureService = null;
    }

    @Override
    public Single<Furniture> createFurniture(String authorization, Furniture furniture) {
        return mFurnitureService.createFurniture(authorization, furniture);
    }

    @Override
    public Single<Furniture> getFurniture(String authorization, long id) {
        return mFurnitureService.getFurniture(authorization, id);
    }

    @Override
    public Single<Furniture> updateFurniture(String authorization, Furniture furniture) {
        return mFurnitureService.updateFurniture(authorization, furniture.getId(), furniture);
    }

    @Override
    public Single<Pagination<Furniture>> getFurnitureList(String authorization, int page, int perPage) {
        return mFurnitureService.getFurnitureList(authorization, page, perPage);
    }

    @Override
    public void deleteFurniture(String authorization, Furniture furniture) {
        deleteFurniture(authorization, furniture.getId());
    }

    @Override
    public void deleteFurniture(String authorization, long id) {
        mFurnitureService.deleteFurniture(authorization, id);
    }
}
