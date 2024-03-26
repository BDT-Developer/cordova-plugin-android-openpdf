package nz.bdt.androidopenpdf;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import androidx.core.content.FileProvider;
import java.io.File;

public class OpenPdf extends CordovaPlugin {
    private static final int PICK_PDF_REQUEST_CODE = 1; // Unique request code for picking PDFs
    private CallbackContext callbackContext; // To keep a reference to the callback context

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;

        if ("openPdfUsingSAF".equals(action)) {
            openPdfWithSAF();
            return true;
        } else if ("openDownloadedFile".equals(action)) {
            try {
                String filePath = args.getString(0);
                openDownloadedFile(filePath);
                return true;
            } catch (Exception e) {
                callbackContext.error("Error processing the file path: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    private void openPdfWithSAF() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        cordova.setActivityResultCallback(this);
        cordova.getActivity().startActivityForResult(intent, PICK_PDF_REQUEST_CODE);
    }

    private void openDownloadedFile(String filePath) {
        File file = new File(filePath);
        Uri contentUri = FileProvider.getUriForFile(cordova.getActivity(),
                cordova.getActivity().getApplicationContext().getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(contentUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cordova.getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            openFile(uri);
        } else {
            callbackContext.error("No PDF selected or an error occurred.");
        }
    }

    private void openFile(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            cordova.getActivity().startActivity(intent);
        } catch (Exception e) {
            callbackContext.error("Failed to open the file: " + e.getMessage());
        }
    }

}
