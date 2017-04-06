/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.atos.jsonschema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author A416865
 */
public class JSONValidator {

    public static JSONObject getJSONObjectOrNULL(String json) {
        JSONObject ret = null;
        try {
            ret = new JSONObject(new JSONTokener(json));
        } catch (Exception e) {
            // do nothing
        }
        return ret;
    }

    public static boolean isJSONValidatedWithSchema(JSONObject json, JSONObject rawSchema) {
        try {
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(json); // throws a ValidationException if this object is invalid
        } catch (ValidationException ex) {
            return false;
        }
        return true;
    }

    public static String formatJSON(String json) {
        String ret = null;
        try {
            JSONObject jo = new JSONObject(json);
            ret = jo.toString(2);
        } catch (Exception e) {
            // do nothing
        }
        return ret;
    }

}
