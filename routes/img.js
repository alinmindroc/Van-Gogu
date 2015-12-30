var express = require('express');
var router = express.Router();
var exec = require('child_process').exec;
var spawn = require('child_process').spawn;

/* GET users listing. */
router.get('/:id', function(req, res, next) {
	if (req.params.id.length != 1){
		res.send("must send a number between 0 and 4 as arg");
	} else {
		process.chdir('logic');
		// exec('ls -l', function(error, stdout, stderr) {
			// console.log(stdout);
		// });
		
		var child = spawn('java', ['app.VanGogh', req.params.id]);
/*
		var cmd = 'cd logic; java app.VanGogh ' + req.params.id;

		res.sendStatus(500);
		exec(cmd, function(error, stdout, stderr) {
			console.log(stdout);
		});
*/
	}
});

module.exports = router;
