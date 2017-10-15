package kr.co.landvibe.handicraft.data.source.auth;


import android.support.annotation.NonNull;

import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.data.domain.NaverUser;

public interface AuthDataSource {

    Maybe<String> createAuth(@NonNull String accessToken, @NonNull NaverUser naverUser);

    Maybe<String> getAuth(@NonNull String accessToken);

    Maybe<NaverUser> getNaverUser(@NonNull String accessToken, @NonNull String tokenType);
}
