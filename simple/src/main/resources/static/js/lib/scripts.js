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

            request.success(function (response) {
                $scope.generateCount = response.totalCount;
                $scope.generateStatus = "Ok. Generated " + $scope.generateCount + " reviews.";
                $scope.customerIds = response.customerIds;
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
                promise.success(function (response) {
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