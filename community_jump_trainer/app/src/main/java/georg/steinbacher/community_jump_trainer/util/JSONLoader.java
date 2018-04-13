package georg.steinbacher.community_jump_trainer.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import georg.steinbacher.community_jump_trainer.R;

/**
 * Created by stge on 13.04.18.
 */

class JSONLoader {

    static JSONObject get(Context context, int rawId) {
        try {
            return new JSONObject(getJSONStringFromFile(context, rawId));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getJSONStringFromFile(Context context, int rawId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(rawId);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
