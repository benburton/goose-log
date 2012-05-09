/*global ActiveXObject:false */

(function(window) {
    var GL = {},
		provider,
        loggingServerUrl,
        cacheSize = 1,
        errorObj = window.onerror,
        xhrObj = null;

    window.XMLHttpRequest ? xhrObj = new window.XMLHttpRequest() : xhrObj = new ActiveXObject("Microsoft.XMLHTTP");


    GL._errors = [];
    GL.handleError = function(message, url, line) {
        try {
            GL._errors.push({
				"provider" 	: provider,
				"origin" 	: url,
				"details" : {
					"message" 	: message,
					"line"		: line
				}
			});
            if (xhrObj && GL._errors.length >= cacheSize) {
                errorObj && errorObj.apply(null, arguments);
                xhrObj.open('POST', loggingServerUrl, true);
                xhrObj.setRequestHeader("Content-Type","application/json");
                xhrObj.send(JSON.stringify(GL._errors));
                xhrObj.onreadystatechange = function (oEvent) {
                    if (xhrObj.readyState === 4) {
                        GL._errors = [];
                    }
                }
            }
        }
        catch (exc) {
            return;
        }
    };

    GL.init = function(config) {
        loggingServerUrl = config.url;
		provider = config.provider || "";
        cacheSize = config.cacheSize || 1;
        window.onerror = GL.handleError;
    }

    window.GL = GL;
}(this));

/*
	GL.init({
	    url : 'http://localhost:8080/error-log,
	    cacheSize : 5,
	    provider : 'client-errors'
	});
*/