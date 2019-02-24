(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
'use strict';

var xhr = require('./net/xhr'),
    isString = function(str) {
        return Object.prototype.toString.call(str) === '[object String]';
    },
    isFunction = function(fn) {
        return Object.prototype.toString.call(fn) === '[object Function]';
    },
    // Do the eval trick, since JSON object not present
    customParse = function(chunk) {
        if (!chunk || !/^[\{|\[].*[\}|\]]$/.test(chunk)) {
            throw new Error('parseerror');
        }
        return eval('(' + chunk + ')');
    },
    parse = function(chunk, success, error) {
        var jsonObj;
        try {
            jsonObj = typeof JSON !== 'undefined' ? JSON.parse(chunk) : customParse(chunk);
        } catch (ex) {
            if (isFunction(error)) {
                error('parsererror');
            }
            return;
        }
        // No parse error proceed to success
        if (jsonObj && isFunction(success)) {
            success(jsonObj);
        }
    },
    /**
     * @param {String} url A string containing the URL to which the request is sent.
     * @param {Object} url A set of key/value pairs that configure the Ajax request.
     * @return {XMLHttpRequest} The XMLHttpRequest object for this request.
     * @method ajax
     */
    ajax = function(url, options) {
        // Do all prerequisite checks
        if (!url) {
            return;
        }

        // Set arguments if first argument is not string
        if (!isString(url)) {
            options = url;
            url = options.url;
        }

        // Check if all mandatory attributes are present
        if (!url ||
            !options ||
            !(options.success || options.error || options.complete)) {
            return;
        }

        var offset = 0,
            token = options.delimiter || '\n\n',
            onChunk = function(text, finalChunk) {
                var chunk = text.substring(offset),
                    start = 0,
                    finish = chunk.indexOf(token, start),
                    successFn = options.success,
                    errorFn = options.error,
                    subChunk;

                if (finish === 0) { // The delimiter is at the beginning so move the start
                    start = finish + token.length;
                }

                while ((finish = chunk.indexOf(token, start)) > -1) {
                    subChunk = chunk.substring(start, finish);
                    if (subChunk) {
                        parse(subChunk, successFn, errorFn);
                    }
                    start = finish + token.length; // move the start
                }
                offset += start; // move the offset

                // Get the remaning chunk
                chunk = text.substring(offset);
                // If final chunk and still unprocessed chunk and no delimiter, then execute the full chunk
                if (finalChunk && chunk && finish === -1) {
                    parse(chunk, successFn, errorFn);
                }
            };

        // Assign onChunk to options
        options.onChunk = onChunk;

        return xhr.send(url, options);
    };

module.exports = {
    flow: ajax
};

},{"./net/xhr":2}],2:[function(require,module,exports){
'use strict';

function send(url, options) {
    if (!url || !options) {
        return;
    }

    var xhr = new XMLHttpRequest(),
        state = {
            UNSENT: 0,
            OPENED: 1,
            HEADERS_RECEIVED: 2,
            LOADING: 3,
            DONE: 4
        },
        noop = function() {},
        method = (options.method || '').toUpperCase(),
        headers = options.headers,
        onChunk = options.onChunk || noop,
        errorFn = options.error || noop,
        completeFn = options.complete || noop,
        addContentHeader = method === 'POST',
        isChunked = false,
        timer;

    xhr.open(method || 'GET', url, true);

    // Attach onreadystatechange
    xhr.onreadystatechange = function() {
        var encoding,
            chromeObj,
            loadTimes,
            chromeSpdy;
        if (xhr.readyState === state.HEADERS_RECEIVED) {
            encoding = xhr.getResponseHeader('Transfer-Encoding') || '';
            encoding = encoding.toLowerCase();
            isChunked = encoding.indexOf('chunked') > -1 ||
                        encoding.indexOf('identity') > -1; // fix for Safari
            if (!isChunked) {
                chromeObj = window.chrome;
                loadTimes = chromeObj && chromeObj.loadTimes && chromeObj.loadTimes();
                chromeSpdy = loadTimes && loadTimes.wasFetchedViaSpdy;
                isChunked = !!(xhr.getResponseHeader('X-Firefox-Spdy') || chromeSpdy);
            }
        } else if (xhr.readyState === state.LOADING) {
            if (isChunked && xhr.responseText) {
                onChunk(xhr.responseText);
            }
        } else if (xhr.readyState === state.DONE) {
            // clear timeout first
            clearTimeout(timer);
            // Check for error first
            if (xhr.status < 200 || xhr.status > 299) {
                errorFn(xhr.statusText);
            } else {
                onChunk(xhr.responseText, true);
            }
            // Call complete at the end
            completeFn(xhr.statusText);
        }
    };

    // Add headers
    if (headers) {
        for (var key in headers) {
            xhr.setRequestHeader(key, headers[key]);
            if (key.toLowerCase() === 'content-type') {
                addContentHeader = false;
            }
        }
    }
    if (addContentHeader) {
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    }

    // Add timeout
    if (options.timeout) {
        timer = setTimeout(function() {
            xhr.abort();
            clearTimeout(timer);
        }, options.timeout);
    }

    // Set credentials
    if (options.hasOwnProperty("withCredentials")) {
        xhr.withCredentials = options.withCredentials;
    } else {
        xhr.withCredentials = true;
    }

    xhr.send(options.data);

    return xhr;
}

module.exports = {
    send: send
};

},{}],3:[function(require,module,exports){
angular.module('app', [])
    .controller('main', function ($scope, $http, $timeout, $q) {

        var jsonpipe = require('./jsonpipe');

        $scope.custCount = 3;
        $scope.reviewCountMin = 1000;
        $scope.reviewCountMax = 2000;
        $scope.showLoadResults = false;
        $scope.customerIds = [];

        $scope.generate = function () {

            $scope.generateStatus = 'Working...';
            $scope.customerIds = [];
            $scope.reviews = [];
            $scope.showLoadResults = false;

            var request = $http({
                method: 'POST',
                url: '/rest/generate',
                data: {
                    custCount: $scope.custCount,
                    reviewCountMin: $scope.reviewCountMin,
                    reviewCountMax: $scope.reviewCountMax
                }
            });

            request.then(function (response) {
                $scope.generateCount = response.data.totalCount;
                $scope.generateStatus = "Ok. Generated " + $scope.generateCount + " reviews.";
                $scope.customerIds = response.data.customerIds;
            });
        };

        function load(useScala) {
            $scope.startTime = new Date().getTime();
            $scope.loadTime = 0;
            $scope.loadStatus = "Loading...";
            $scope.showLoadResults = false;
            $scope.reviews = [];

            var loadAll = requestAllReviews($scope.customerIds, useScala);
            loadAll.then(function () {
                var endTime = new Date().getTime();
                $scope.loadTime = endTime - $scope.startTime;
                $scope.loadStatus = "Ok";
                $scope.showLoadResults = true;
            });
        };

        $scope.loadJava = function () {
            load(false)
        }

        $scope.loadScala = function () {
            load(true)
        }

        $scope.loadScalaStream = function () {
            $scope.startTime = new Date().getTime();
            $scope.loadTime = 0;
            $scope.loadStatus = "Loading...";
            $scope.showLoadResults = true;
            $scope.reviews = [];
            for (var i = 0; i < $scope.customerIds.length; i++) {
                var id = $scope.customerIds[i];
                var url = 'http://localhost:8081/rest/loadStream?customerId=' + id
                jsonpipe.flow(url, {
                    "success": function (data) {
                        //console.log("Chunk received")
                        var reviews = $scope.reviews;
                        reviews = reviews.concat(data)
                        $scope.reviews = reviews;
                        $scope.$applyAsync();
                    },
                    "timeout": 5000, // Number. Set a timeout (in milliseconds) for the request
                    "method": "GET",
                    "complete": function (statusText) {
                        console.log("Chunked transfer complete with status: " + statusText)
                        $scope.$applyAsync();
                    },
                });
            }
            $scope.loadStatus = "Ok";
            //$scope.$applyAsync();
        }

        function requestAllReviews(customerIds, useScala) {
            var promises = [];
            for (var i = 0; i < customerIds.length; i++) {
                var id = customerIds[i];
                var promise = $http({
                    method: 'GET',
                    url: '/rest/load',
                    params: {
                        customerId: id,
                        useScala: useScala
                    }
                });
                promise.then(function (response) {
                    var customerReviews = response;
                    $scope.reviews = $scope.reviews.concat(customerReviews);
                })
                promises.push(promise);
            }
            return $q.all(promises);
        }

        $scope.customerFilter = function (tableCustomerId) {
            return function (tableReview) {
                return tableReview.customerId == tableCustomerId ? true : false;
            }
        };
    });
},{"./jsonpipe":1}]},{},[3]);
