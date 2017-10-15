package kr.co.landvibe.handicraft.data.source.member;


import android.support.annotation.NonNull;

import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.GlobalApp;
import kr.co.landvibe.handicraft.data.domain.Member;
import kr.co.landvibe.handicraft.data.source.member.local.MemberCacheService;
import kr.co.landvibe.handicraft.data.source.member.remote.MemberService;
import kr.co.landvibe.handicraft.error.InternalServerException;
import kr.co.landvibe.handicraft.error.UnAuthorizationException;
import kr.co.landvibe.handicraft.utils.LogUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MemberRepository implements MemberDataSource {

    private static MemberRepository INSTANCE;

    private MemberService mMemberService;

    private MemberCacheService mMemberCacheService;

    private MemberRepository() {
        OkHttpClient okHttpClient = GlobalApp.getOkHttpClientInstance();
        Retrofit retrofit = GlobalApp.getRetrofitInstance(okHttpClient);

        mMemberService = retrofit.create(MemberService.class);

        mMemberCacheService = MemberCacheService.getInstance();
    }

    public static MemberRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemberRepository();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
        mMemberService = null;
        mMemberCacheService.destroyInstance();
        mMemberCacheService = null;
    }

    @Override
    public Maybe<Member> getMember(@NonNull String accessToken, @NonNull String id) {
        return mMemberService.getMember(
                accessToken,
                id
        ).flatMap(response -> {
            if (response.isSuccessful()) {
                LogUtils.d("Response Code : " + response.code());

                Member member = response.body();
                cacheMember(member);
                return Maybe.just(member);
            } else {
                switch (response.code()) {
                    case 401:
                        return Maybe.error(new UnAuthorizationException(response.errorBody().string()));
                    case 500:
                        return Maybe.error(new InternalServerException(response.errorBody().string()));
                    default:
                        return Maybe.empty();
                }
            }
        });
    }

    @Override
    public Maybe<Member> updateMember(@NonNull String accessToken, @NonNull String id, @NonNull Member user) {
        return mMemberService.updateMember(
                accessToken,
                id,
                user
        ).flatMap(response -> {
            if (response.isSuccessful()) {
                LogUtils.d("Response Code : " + response.code());

                Member member = response.body();
                cacheMember(member);
                return Maybe.just(member);
            } else {
                switch (response.code()) {
                    case 401:
                        return Maybe.error(new UnAuthorizationException(response.errorBody().string()));
                    case 500:
                        return Maybe.error(new InternalServerException(response.errorBody().string()));
                    default:
                        return Maybe.empty();
                }
            }
        });
    }

    @Override
    public Maybe<String> deleteMember(@NonNull String accessToken, @NonNull String id) {
        return mMemberService.deletetMember(
                accessToken,
                id
        ).flatMap(response -> {
            if (response.isSuccessful()) {
                LogUtils.d("Response Code : " + response.code());

                cacheMember(id);
                return Maybe.just("");
            } else {
                switch (response.code()) {
                    case 401:
                        return Maybe.error(new UnAuthorizationException(response.errorBody().string()));
                    case 500:
                        return Maybe.error(new InternalServerException(response.errorBody().string()));
                    default:
                        return Maybe.empty();
                }
            }
        });
    }

    private void cacheMember(Member member) {
        if (mMemberCacheService.findById(member.getId()) == null) {
            mMemberCacheService.create(member);
        } else {
            mMemberCacheService.update(member);
        }
    }
    private void cacheMember(String id) {
        mMemberCacheService.delete(id);
    }
}
