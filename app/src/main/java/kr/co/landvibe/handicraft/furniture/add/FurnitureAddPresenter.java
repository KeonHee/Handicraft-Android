package kr.co.landvibe.handicraft.furniture.add;


import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import kr.co.landvibe.handicraft.data.source.furniture.FurnitureRepository;
import kr.co.landvibe.handicraft.utils.LogUtils;
import kr.co.landvibe.handicraft.utils.SharedPreferenceUtils;
import retrofit2.HttpException;

import static kr.co.landvibe.handicraft.utils.DefineUtils.SPREF_UID;

public class FurnitureAddPresenter implements FurnitureAddContract.Presenter {

    private FurnitureAddContract.View view;
    private Context context;

    private FurnitureRepository mFurnitureRepository;

    private CompositeDisposable disposables;

    @Override
    public void attachView(FurnitureAddContract.View view, Context context) {
        this.view = view;
        this.context = context;
        mFurnitureRepository = FurnitureRepository.getInstance();
        disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        this.view = null;
        this.context = null;
        mFurnitureRepository.destroyInstance();
        mFurnitureRepository = null;
        disposables.dispose();
        disposables = null;
    }

    @Override
    public void registerFurniture(Furniture furniture) {
        String craftToken = SharedPreferenceUtils.getStringPreference(context, SPREF_UID);
        Disposable furnitureDisposable = mFurnitureRepository.createFurniture("craft " + craftToken, furniture)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Furniture>() {
                    @Override
                    public void onSuccess(@NonNull Furniture furniture) {
                        view.hideLoading();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
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

        // view change
        view.backToMainActivity();
    }
}
