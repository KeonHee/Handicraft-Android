package kr.co.landvibe.handicraft.data.source.member.local;


import io.realm.Realm;
import kr.co.landvibe.handicraft.data.domain.Member;

public class MemberCacheService {

    private static MemberCacheService INSTANCE;

    private Realm mRealm;

    private MemberCacheService() {
        mRealm = Realm.getDefaultInstance();
    }

    public static MemberCacheService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemberCacheService();
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        mRealm.close();
        INSTANCE = null;
    }

    public Member findById(String id) {
        return mRealm.where(Member.class).equalTo("id", id).findFirst();
    }

    public void create(Member member) {
        mRealm.executeTransaction(realm -> {
            Member memberRealm = realm.createObject(Member.class, member.getId());
            memberRealm.bind(member);
        });
    }

    public void update(Member member) {
        Member memberRealm = findById(member.getId());
        mRealm.executeTransaction(realm ->
                memberRealm.bind(member));
    }

    public void delete(String id) {
        Member memberRealm = findById(id);
        memberRealm.deleteFromRealm();
    }


}
