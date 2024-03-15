package org.example.openpdf;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;

public class OpenPdf extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("openPdf".equals(action)) {
            String filePath = args.getString(0); // Get the file path from JavaScript
            openPdf(filePath, callbackContext);
            return true;
        }
        return false;
    }

    private void openPdf(String filePath, CallbackContext callbackContext) {
        try {
            File file = new File(filePath);
            Uri uri = FileProvider.getUriForFile(cordova.getActivity(), cordova.getActivity().getApplicationContext().getPackageName() + ".provider", file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cordova.getActivity().startActivity(intent);

            callbackContext.success(); // PDF opened successfully
        } catch (Exception e) {
            callbackContext.error("Failed to open PDF: " + e.toString());
        }
    }
}
