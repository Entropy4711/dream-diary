module.exports = function(grunt) {
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),
		compass: {                  // Task
			dist: {                   // Target
			  options: {              // Target options
			    sassDir: 'sass',
			    cssDir: 'css',
			    environment: 'production'
			  }
			}
		},
		watch: {
			css: {
				files: '**/*.scss',
				tasks: ['sass']
			}
		}
	});
	grunt.loadNpmTasks('grunt-contrib-compass');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.registerTask('default',['watch', 'compass']);
}