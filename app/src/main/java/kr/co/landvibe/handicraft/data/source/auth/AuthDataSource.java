package kr.co.landvibe.handicraft.data.source.auth;


import android.support.annotation.NonNull;

import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.data.domain.Member;

public interface AuthDataSource {

    Maybe<String> createAuth(@NonNull String accessToken, @NonNull Member member);

    Maybe<String> getAuth(@NonNull String accessToken);

    Maybe<Member> getNaverUserInfo(@NonNull String accessToken, @NonNull String tokenType);
}
