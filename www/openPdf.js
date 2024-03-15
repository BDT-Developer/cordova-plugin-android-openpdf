var exec = require('cordova/exec');

exports.openPdf = function (filePath, success, error) {
    exec(success, error, 'OpenPdf', 'openPdf', [filePath]);
};

exports.selectAndOpenPdf = function (success, error) {
    exec(success, error, 'OpenPdf', 'selectAndOpenPdf', []);
};
