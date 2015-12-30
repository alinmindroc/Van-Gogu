var myApp = angular.module('myApp', []);

myApp.controller('myAppCtrl', function ($scope, $timeout, $http) {

	$scope.colorScheme = '';
	$scope.imageReady = false;
	$scope.imageSrc = '';

	$scope.getPhoto = function() {		
		$('#progress').css('animation', 'progress 40s 1 forwards');
		$timeout(function () {
			$scope.imageReady = true;
			$scope.imageSrc = '/images/result.png';
			$('#progress').css('animation', 'refresh 40s 1 backwards');
		}, 40000);

		$http.get('/img/' + $scope.colorScheme);
	}
});
