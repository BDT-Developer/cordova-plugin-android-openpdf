var exec = require('cordova/exec');

exports.openPdfUsingSAF = function (success, error) {
    exec(success, error, "OpenPdf", "openPdfUsingSAF", []);
};