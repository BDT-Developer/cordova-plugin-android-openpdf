# cordova-plugin-android-openpdf

built to comply with: Storage Access Framework (SAF)
to use with Android 13 permisions

still in development mode, try to handle downloading a pdf file from the we and saving to cordova.file.externalCacheDirectory

trying to resolve the below.

//DOWNLOAD AND SAVE FILE ***********************************************************************

function downloadAndSaveFile(dir, url, filename) {
    // Use fetch to download the file
    fetch(url)
        .then(response => response.blob()) // Convert the response to a blob
        .then(blob => {
            // Resolve the local file system URL where to save the file
            window.resolveLocalFileSystemURL(dir, function (dirEntry) {
                // Get a file entry for the file in the specified directory
                dirEntry.getFile(filename, { create: true, exclusive: false }, function (fileEntry) {
                    // Create a FileWriter object for our FileEntry
                    fileEntry.createWriter(function (fileWriter) {
                        fileWriter.onwriteend = function () {
                            console.log("Download successful, file written to: " + fileEntry.nativeURL);
                        };
                        fileWriter.onerror = function (e) {
                            console.error("Write failed: " + e.toString());
                        };
                        // Write the content of the blob to the file
                        fileWriter.write(blob);
                    });
                }, onError);
            }, onError);
        })
        .catch(error => {
            console.error("Download error: ", error);
        })
    function onError(error) {
        console.error("Error: ", error);
    }
}

downloadAndSaveFile(cordova.file.externalCacheDirectory, 'https://www.xxxx.xx/xxx.pdf', 'xxx.pdf');

//OPEN FILE ***********************************************************************

// this works, but get error: access denined

cordova.InAppBrowser.open('file:///storage/emulated/0/Android/data/xxxx/cache/xxx.pdf', '_blank', 'location=yes');

