var myApp = angular.module('myApp', []);

myApp.controller('myAppCtrl', function ($scope, $timeout) {
  
	$scope.seed = '';
	$scope.showImage = false;

	$scope.generatePhoto = function() {		
		$('#progress').css('animation', 'progress 4s 1 forwards');
		$timeout(function () {
    		$scope.showImage = true;
    		$('#progress').css('animation', 'refresh 2s 1 backwards');
		}, 4000);
	}


});
