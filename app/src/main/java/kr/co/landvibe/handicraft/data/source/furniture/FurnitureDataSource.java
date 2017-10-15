package kr.co.landvibe.handicraft.data.source.furniture;


import io.reactivex.Single;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import kr.co.landvibe.handicraft.data.support.Pagination;

public interface FurnitureDataSource {

    Single<Furniture> createFurniture(String authorization, Furniture furniture);

    Single<Furniture> getFurniture(String authorization,long id);

    Single<Furniture> updateFurniture(String authorization, Furniture furniture);

    Single<Pagination<Furniture>> getFurnitureList(String authorization, int page, int perPage);

    void deleteFurniture(String authorization, Furniture furniture);

    void deleteFurniture(String authorization, long id);
}
