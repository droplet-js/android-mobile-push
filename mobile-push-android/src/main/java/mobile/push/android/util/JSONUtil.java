package mobile.push.android.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class JSONUtil {

    private JSONUtil() {
    }

    public static Map<String, Object> toMap(String jsonStr) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                JSONObject json = new JSONObject(jsonStr);
                Iterator<String> keys = json.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!json.isNull(key)) {
                        Object value = json.get(key);
                        map.put(key, value);
                    }
                }
            }
        } catch (JSONException e) {
        }
        return map;
    }

    public static String toJsonString(Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
}
