var express = require('express');
var router = express.Router();
var exec = require('child_process').exec;
var spawn = require('child_process').spawn;
var fs = require('fs');

router.post('/:id', function(req, res, next) {
	if (req.params.id.length != 1){
		res.send("must send a number between 0 and 4 as arg");
	} else {
		console.log('generating ' + req.params.id);

		var current_date = (new Date()).valueOf().toString();
		var resultPath = current_date + '.png';

		var child = spawn('java', ['app.VanGogh', req.params.id, resultPath], {cwd: 'logic'});
		child.on('error', function(e) {console.log(e)});

		res.send(resultPath);
	}
});

router.delete('/:path', function(req, res, next){
	//create results dir
	try {
		fs.mkdirSync('logic/results');
	} catch(e) {
		if ( e.code != 'EEXIST' ) throw e;
	}
	
	var resultPath = 'logic/results/' + req.params.path;

	console.log('moving to ' + resultPath)
	fs.rename('logic/' + req.params.path, resultPath);
});

module.exports = router;
