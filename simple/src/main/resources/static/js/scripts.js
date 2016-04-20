angular.module('app', [])
    .controller('main', function ($scope, $http, $timeout, $q) {
        $scope.custCount = 4;
        $scope.reviewCountMin = 2000;
        $scope.reviewCountMax = 5000;
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

        $scope.load = function () {
            $scope.startTime = new Date().getTime();
            $scope.loadTime = 0;
            $scope.loadStatus = "Loading...";
            $scope.showLoadResults = false;
            $scope.reviews = [];

            var loadAll = requestAllReviews($scope.customerIds);
            loadAll.then(function () {
                var endTime = new Date().getTime();
                $scope.loadTime = endTime - $scope.startTime;
                $scope.loadStatus = "Ok";
                $scope.showLoadResults = true;
            });
        };

        function requestAllReviews(customerIds) {
            var promises = [];
            for (var i = 0; i < customerIds.length; i++) {
                var id = customerIds[i];
                var promise = $http({
                    method: 'GET',
                    url: '/rest/load',
                    params: {customerId: id}
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