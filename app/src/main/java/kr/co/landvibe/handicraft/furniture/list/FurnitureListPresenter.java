package kr.co.landvibe.handicraft.furniture.list;


import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import kr.co.landvibe.handicraft.data.source.furniture.FurnitureRepository;
import kr.co.landvibe.handicraft.data.support.Pagination;
import kr.co.landvibe.handicraft.furniture.list.adapter.contract.FurnitureListAdapterContract;
import kr.co.landvibe.handicraft.listener.OnItemClickListener;
import kr.co.landvibe.handicraft.utils.LogUtils;
import kr.co.landvibe.handicraft.utils.SharedPreferenceUtils;
import retrofit2.HttpException;

import static kr.co.landvibe.handicraft.utils.DefineUtils.SPREF_UID;

public class FurnitureListPresenter implements FurnitureListContract.Presenter, OnItemClickListener {

    private FurnitureListContract.View view;
    private Context context;

    private FurnitureListAdapterContract.View mAdapterView;
    private FurnitureListAdapterContract.Model mAdapterModel;

    private FurnitureRepository mFurnitureRepository;

    private CompositeDisposable disposables;

    @Override
    public void attachView(FurnitureListContract.View view, Context context) {
        this.view = view;
        this.context=context;
        mFurnitureRepository = FurnitureRepository.getInstance();
        disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        this.view = null;
        this.context=null;

        mAdapterModel = null;
        mAdapterView = null;

        mFurnitureRepository.destroyInstance();
        mFurnitureRepository = null;
        disposables.dispose();
    }

    @Override
    public void setFurnitureListAdapterModel(FurnitureListAdapterContract.Model model) {
        mAdapterModel = model;
    }

    @Override
    public void setFurnitureListAdapterView(FurnitureListAdapterContract.View view) {
        mAdapterView = view;
        mAdapterView.setOnItemClickListener(this);
    }

    @Override
    public void loadFurnitureList() {

        String craftToken = SharedPreferenceUtils.getStringPreference(context, SPREF_UID);
        Disposable furnitureDisposable = mFurnitureRepository.getFurnitureList("craft "+craftToken,0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Pagination<Furniture>>() {
                    @Override
                    public void onSuccess(@NonNull Pagination<Furniture> furniturePagination) {
                        List<Furniture> furnitures = furniturePagination.getContents();
                        mAdapterModel.setListData(furnitures);
                        mAdapterView.notifyAdapter();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mAdapterModel.setListData(loadFakeFurnitureData());
                        mAdapterView.notifyAdapter();

                        if (e instanceof HttpException) {
                            LogUtils.i(e.getMessage());
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 400:
                                    break;
                                case 401:
                                    break;
                                case 403:
                                    break;
                                case 500:
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            LogUtils.e(e.getMessage());
                        }
                    }
                });

        disposables.add(furnitureDisposable);
    }

    @Override
    public void onItemClick(Object object) {
        Furniture furniture = (Furniture) object;
        LogUtils.d("clicked : " + furniture.getId());

        // move to furniture detail page
        view.moveToFurnitureDetailActivity(furniture.getId());
    }

    private List<Furniture> loadFakeFurnitureData(){
        Calendar createdAt = Calendar.getInstance();
        List<Furniture> list = new ArrayList<>();
        list.add(new Furniture(1, "회색의자!", "팝니다", "a+", "1년만 쓴건데 상태좋아요"
                , null , "의자", "일룸", "3년", 20000, 15, 15, 15, "서울", createdAt, createdAt ,false ));
        list.add(new Furniture(1, "회색의자!", "팝니다", "a+", "1년만 쓴건데 상태좋아요"
                , null , "의자", "일룸", "2년", 10000, 15, 15, 15, "대전", createdAt, createdAt ,false ));
        list.add(new Furniture(1, "회색의자!", "팝니다", "a+", "1년만 쓴건데 상태좋아요"
                , null , "의자", "일룸", "4년", 12000, 15, 15, 15, "대구", createdAt, createdAt ,false ));
        list.add(new Furniture(1, "회색의자!", "팝니다", "a+", "1년만 쓴건데 상태좋아요"
                , null , "의자", "일룸", "5년", 8000, 15, 15, 15, "부산", createdAt, createdAt ,false ));
        list.add(new Furniture(1, "회색의자!", "팝니다", "a+", "1년만 쓴건데 상태좋아요"
                , null , "의자", "일룸", "6년", 5000, 15, 15, 15, "인천", createdAt, createdAt ,false ));
        list.add(new Furniture(1, "회색의자!", "팝니다", "a+", "1년만 쓴건데 상태좋아요"
                , null , "의자", "일룸", "1년", 40000, 15, 15, 15, "서울", createdAt, createdAt ,false ));
        return list;
    }
}
