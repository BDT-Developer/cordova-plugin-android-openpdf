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
    private static final int PICK_PDF_FILE = 1; // Request code for selecting a PDF file
    private CallbackContext callbackContext; // To keep reference to the callback context

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if ("openPdf".equals(action)) {
            String filePath = args.getString(0); // Get the file path from JavaScript
            openPdf(filePath, callbackContext);
            return true;
        } else if ("selectAndOpenPdf".equals(action)) {
            selectPdf();
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

    private void selectPdf() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        cordova.startActivityForResult(this, intent, PICK_PDF_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FILE && resultCode == android.app.Activity.RESULT_OK) {
            if (data != null) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                openPdfFromUri(uri);
            }
        }
    }

    private void openPdfFromUri(Uri uri) {
        try {
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
