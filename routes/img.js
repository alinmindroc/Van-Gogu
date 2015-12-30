var express = require('express');
var router = express.Router();
var exec = require('child_process').exec;
var spawn = require('child_process').spawn;

router.get('/delete', function(req, res, next) {
	var child = spawn('rm', ['-f', 'logic/*.png']);
});

router.get('/:id', function(req, res, next) {
	if (req.params.id.length != 1){
		res.send("must send a number between 0 and 4 as arg");
	} else {
		console.log('generating ' + req.params.id);

		process.chdir('logic');
		var child = spawn('java', ['app.VanGogh', req.params.id]);
	}
});

module.exports = router;
