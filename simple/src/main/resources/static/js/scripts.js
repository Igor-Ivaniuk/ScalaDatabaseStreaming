angular.module('app', [])
    .controller('main', function ($scope, $http) {
        $scope.customerId = 1;
        $scope.reviewCount = 1;

        $scope.generate = function () {

            $scope.generateStatus = 'Working...'

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
            })
        }
    })