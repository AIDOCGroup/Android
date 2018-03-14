package me.aidoc.client.util.custom.country_region_picker;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by android on 17/10/17.
 */

public abstract class ExceptionCallback {
    public abstract void onIOException(IOException e);
    public abstract void onJSONException(JSONException e);
}
