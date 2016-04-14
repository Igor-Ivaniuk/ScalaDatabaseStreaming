angular.module('app', [])
    .controller('main', function ($scope, $http, $timeout) {
        $scope.custCount = 5;
        $scope.reviewCountMin = 5000;
        $scope.reviewCountMax = 10000;
        $scope.showLoadResults = false;
        $scope.customerIds = [];

        $scope.generate = function () {

            $scope.generateStatus = 'Working...';
            $scope.customerIds = [];

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
            $scope.reviews = null;
            $scope.showLoadResults = false;

            var request = $http({
                method: 'GET',
                url: '/rest/load',
                params: {customerIds: $scope.customerIds}
            });

            request.then(function (response) {
                $scope.reviews = response.data;
                var endTime = new Date().getTime();
                $scope.loadTime = endTime - $scope.startTime;
                $scope.loadStatus = "Ok";
                $scope.showLoadResults = true;
            });
        };
    });