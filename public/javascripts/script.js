var drawApp = angular.module('vanGogu', []);

drawApp.controller('vanGoguCtrl', function ($scope, $timeout, $http) {

	$scope.colorScheme = '';
	$scope.imageReady = false;
	$scope.imageName = "";

	var schemeIndexes = {'Spring':0, 'Summer':1, 'Autumn':2, 'Winter':3, 'Alien':4}

	$scope.getPhoto = function() {
		$http.post('/img/' + schemeIndexes[$scope.colorScheme]).success(function(data, status) {
			$scope.imagePath = data + "?please";
		});

		$('#progress').css('animation', 'progress 40s 1 forwards');
		$timeout(function () {
			$scope.imageReady = true;
			$scope.imageName = $scope.imagePath;
			$('#progress').css('animation', 'refresh 40s 1 backwards');
		}, 40000);
	};

	$scope.deleteImg = function(){
		$http.delete('/img/' + $scope.imageName);
	}
});
