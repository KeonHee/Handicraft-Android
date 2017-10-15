package kr.co.landvibe.handicraft.furniture.detail;


import android.content.Context;

import kr.co.landvibe.handicraft.data.domain.Furniture;

public interface FurnitureDetailContract {

    interface View{

        void showLoading();

        void hideLoading();

        void showContactDialog();

        void bindData(Furniture furniture);
    }

    interface Presenter{

        void attachView(FurnitureDetailContract.View view, Context context);

        void detachView();

        void loadFurniture(long id);
    }

}
