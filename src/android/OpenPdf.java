package nz.bdt.androidopenpdf;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.ActivityNotFoundException;

public class OpenPdf extends CordovaPlugin {
    private static final int PICK_PDF_REQUEST_CODE = 1; // The request code
    private CallbackContext callbackContext; // To keep a reference to the callback context

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        if ("openPdfUsingSAF".equals(action)) {
            this.callbackContext = callbackContext;
            openPdfWithSAF();
            return true;
        }
        return false;
    }

    private void openPdfWithSAF() {
        // Create an intent for opening the PDF file picker
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Start the activity to select a PDF file
        cordova.startActivityForResult(this, intent, PICK_PDF_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                // The result data contains a URI for the selected PDF
                Uri uri = data.getData();
                // Open the PDF with an external viewer
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.setDataAndType(uri, "application/pdf");
                viewIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grants temporary read permission
                
                try {
                    cordova.getActivity().startActivity(viewIntent);
                    callbackContext.success(); // PDF viewing was successful
                } catch (ActivityNotFoundException e) {
                    callbackContext.error("No application found to view PDF");
                }
            } else {
                callbackContext.error("No file selected");
            }
        }
    }
}
