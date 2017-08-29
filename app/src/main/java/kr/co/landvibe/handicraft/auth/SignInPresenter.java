package kr.co.landvibe.handicraft.auth;


import com.nhn.android.naverlogin.OAuthLogin;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import kr.co.landvibe.handicraft.data.domain.Member;
import kr.co.landvibe.handicraft.data.source.auth.AuthDataSource;
import kr.co.landvibe.handicraft.data.source.auth.AuthRepository;
import kr.co.landvibe.handicraft.data.source.member.local.MemberCacheRepository;
import kr.co.landvibe.handicraft.utils.LogUtils;
import kr.co.landvibe.handicraft.utils.SharedPreferenceUtils;

import static kr.co.landvibe.handicraft.utils.DefineUtils.SPREF_UID;

public class SignInPresenter implements SignInContract.Presenter {

    private SignInContract.View view;

    private AuthDataSource authDataSource;

    private CompositeDisposable disposables;

    private MemberCacheRepository memberCacheRepository;

    @Override
    public void attachView(SignInContract.View view) {
        this.view = view;
        authDataSource = AuthRepository.getInstance();
        memberCacheRepository = MemberCacheRepository.getInstance();

        disposables = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        this.view = null;
        authDataSource = null;
        disposables.dispose();
        memberCacheRepository.destroyInstance();
    }

    @Override
    public void login(String accessToken, String refreshToken, long expiresAt, String tokenType) {
        requestNaverUserInfo(accessToken, refreshToken, expiresAt, tokenType);
    }

    private void requestNaverUserInfo(String accessToken, String refreshToken, long expiresAt, String tokenType) {
        disposables.add(
                authDataSource.getNaverUserInfo(accessToken, tokenType)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableMaybeObserver<Member>() {
                            @Override
                            public void onSuccess(@NonNull Member member) {
                                createAuthToRemote(accessToken, member);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                // TODO exception 처리
                                LogUtils.e(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.d("Complete get Naver user information");
                            }
                        }));
    }

    private void createAuthToRemote(String accessToken, Member member) {
        disposables.add(
                authDataSource.createAuth(accessToken, member)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(
                                new DisposableMaybeObserver<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String craftToken) {

                                        SharedPreferenceUtils.setStringPreference(view.getContext(), SPREF_UID, craftToken);
                                        LogUtils.d("Craft Token 캐싱 완료");
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        // TODO exception 처리
                                        LogUtils.e(e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {
                                        LogUtils.d("MainActivity로 이동");
                                        view.moveToMainActivity();
                                    }
                                }));
    }


    @Override
    public void checkSessionNaverOauth(OAuthLogin mOAuthLoginInstance) {
        String accessToken = mOAuthLoginInstance.getAccessToken(view.getContext());
        if (accessToken != null) {
            // 인증성공

            authDataSource.getAuth(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(
                            new DisposableMaybeObserver<String>() {
                                @Override
                                public void onSuccess(@NonNull String craftToken) {

                                    // craft token 캐싱
                                    SharedPreferenceUtils.setStringPreference(view.getContext(), SPREF_UID, craftToken);
                                    // TODO 유저정보 로드, from 서버
                                    view.moveToMainActivity();
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    // TODO exception 처리
                                    LogUtils.e(e.getMessage(), e);
                                }

                                @Override
                                public void onComplete() {
                                    LogUtils.d("Complete create auth");
                                }
                            });
        } else {
            view.moveToMainActivity();
        }
    }
}
