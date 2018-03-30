package georg.steinbacher.community_jump_trainer.core;

/**
 * Created by georg on 25.03.18.
 */

public class Equipment {
    private static final String TAG = "Equipment";

    private String mName;
    private Type mType;

    public enum Type {
        GYM,
        HOME
    }

    public Equipment(String name, Type type) {
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public Type getType() {
        return mType;
    }
}
