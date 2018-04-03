package georg.steinbacher.community_jump_trainer.core;

/**
 * Created by stge on 03.04.18.
 */

public class Profile {
    private static final String TAG = "Profile";

    private String mNickName;

    Profile(String nickName) {
        mNickName = nickName;
    }

    public String getNickname() {
        return mNickName;
    }
}
