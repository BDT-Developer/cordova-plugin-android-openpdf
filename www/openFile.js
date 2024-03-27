var exec = require('cordova/exec');

exports.openPdfUsingSAF = function (success, error) {
    cordova.exec(success, error, "OpenFile", "openPdfUsingSAF", []);
};

exports.openDownloadedFile = function (filePath, success, error) {
    cordova.exec(success, error, "OpenFile", "openDownloadedFile", [filePath]);
};
