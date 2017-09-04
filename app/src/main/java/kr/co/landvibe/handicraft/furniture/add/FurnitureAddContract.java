package kr.co.landvibe.handicraft.furniture.add;


import kr.co.landvibe.handicraft.data.domain.Furniture;

public interface FurnitureAddContract {

    interface View{

        void showLoading();

        void hideLoading();

        void backToMainActivity();

        void showPreviewActivity(Furniture furniture);

    }

    interface Presenter{

        void attachView(FurnitureAddContract.View view);

        void detachView();

        void registerFurniture(Furniture furniture);
    }

}
