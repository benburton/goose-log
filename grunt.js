/*global module:false*/
module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    lint: {
      files: ['grunt.js', 'src/main/javascript/src/*.js', 'test/main/javascript/*.js']
    },
    min: {
      dist: {
        src: ['src/main/javascript/libs/json2.js', 'src/main/javascript/libs/microajax.minified.js', 'src/main/javascript/src/logger.js'],
        dest: 'dist/built.min.js',
        separator: ';'
      }
    },
    qunit: {
      files: ['test/**/*.html']
    },
    watch: {
      files: '<config:lint.files>',
      tasks: 'lint qunit'
    },
    jshint: {
      options: {
        curly: true,
        eqeqeq: true,
        immed: true,
        latedef: true,
        newcap: true,
        noarg: true,
        sub: true,
        undef: true,
        boss: true,
        eqnull: true,
        browser: true
      },
      globals: {}
    }
  });

  // Default task.
  grunt.registerTask('default', 'lint qunit');

};
