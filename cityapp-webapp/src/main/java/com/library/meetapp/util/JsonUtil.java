package com.library.meetapp.util;

import com.google.common.base.Strings;
import elemental.json.Json;
import elemental.json.JsonObject;



public class JsonUtil {


    public static String[] getFileNameAndPath(String val){
        if (Strings.isNullOrEmpty(val)) {
            return null;
        }
        JsonObject json = Json.parse(val);
        return new String[] {json.getString("name"), json.getString("path")};
    }


}
