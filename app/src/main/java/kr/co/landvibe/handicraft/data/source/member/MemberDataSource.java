package kr.co.landvibe.handicraft.data.source.member;


import android.support.annotation.NonNull;

import io.reactivex.Maybe;
import kr.co.landvibe.handicraft.data.domain.Member;

public interface MemberDataSource {

    Maybe<Member> getMember(@NonNull String accessToken, @NonNull String id);

    Maybe<Member> updateMember(@NonNull String accessToken, @NonNull String id, @NonNull Member user);

    Maybe<String> deleteMember(@NonNull String accessToken, @NonNull String id);

}
