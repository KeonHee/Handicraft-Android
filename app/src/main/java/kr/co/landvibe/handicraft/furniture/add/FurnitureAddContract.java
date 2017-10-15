package kr.co.landvibe.handicraft.furniture.add;


import android.content.Context;

import kr.co.landvibe.handicraft.data.domain.Furniture;

public interface FurnitureAddContract {

    interface View{

        void showLoading();

        void hideLoading();

        void backToMainActivity();

    }

    interface Presenter{

        void attachView(FurnitureAddContract.View view, Context context);

        void detachView();

        void registerFurniture(Furniture furniture);
    }

}
