angular.module('app', [])
    .controller('main', function ($scope, $http, $timeout) {
        $scope.customerId = 5;
        $scope.reviewCount = 5000;
        $scope.showLoadResults = false;

        $scope.generate = function () {

            $scope.generateStatus = 'Working...';

            var request = $http({
                method: 'POST',
                url: '/rest/generate',
                data: {
                    customerId: $scope.customerId,
                    count: $scope.reviewCount
                }
            });

            request.success(function () {
                $scope.generateStatus = "Ok ";
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
                params: {customer_id: $scope.customerId}
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