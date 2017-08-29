package kr.co.landvibe.handicraft.data.source.member.local;


import io.realm.Realm;
import kr.co.landvibe.handicraft.data.domain.Member;

public class MemberCacheRepository {

    private static MemberCacheRepository INSTANCE;

    private Realm mRealm;

    private MemberCacheRepository() {
        mRealm = Realm.getDefaultInstance();
    }

    public static MemberCacheRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemberCacheRepository();
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

    public void save(Member member) {
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

    public void delete(Member member) {
        Member memberRealm = findById(member.getId());
        memberRealm.deleteFromRealm();
    }


}
