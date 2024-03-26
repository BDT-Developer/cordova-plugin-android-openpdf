var exec = require('cordova/exec');

exports.openPdfUsingSAF = function (success, error) {
    cordova.exec(success, error, "OpenPdf", "openPdfUsingSAF", []);
};

exports.openDownloadedFile = function (filePath, success, error) {
    cordova.exec(success, error, "OpenPdf", "openDownloadedFile", [filePath]);
};
