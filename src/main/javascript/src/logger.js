/*global ActiveXObject:false */

(function() {

    var simpleAjax = {};

    function createXMLHttpRequest()
    {
        if (window.XMLHttpRequest) {
            return new window.XMLHttpRequest();
        }
        //IE7 and below
        else {
            try {
                return new ActiveXObject("MSXML2.XMLHTTP.3.0");
            }
            catch(ex) {
                return null;
            }
        }
    }

    function asyncRequest(config) {
        var xmlHttpRequest = createXMLHttpRequest();
        xmlHttpRequest.open(config.method, config.url, true);
        if (config.data) {
            xmlHttpRequest.setRequestHeader("Content-Type","application/json");
            xmlHttpRequest.setRequestHeader("Connection", "close");
        }
        xmlHttpRequest.onreadystatechange = function (oEvent) {
            if (xmlHttpRequest.readyState === 4) {
                if (xmlHttpRequest.status === 200) {
                    if (typeof config.success === 'function') {
                        config.success(xmlHttpRequest);
                    }
                }
                else {
                    if (typeof config.error === 'function') {
                        config.error(xmlHttpRequest);
                    }
                }
            }
        };
        xmlHttpRequest.send(JSON.stringify(config.data));
    }

    simpleAjax = {
        get : function(config) {
            config.method = 'GET';
            asyncRequest(config);
        },
        post : function(config) {
            config.method = 'POST';
            asyncRequest(config);
        }
    };

    window.error = function(message, url, line) {
/*        simpleAjax.post({

        });*/
    };

}());
