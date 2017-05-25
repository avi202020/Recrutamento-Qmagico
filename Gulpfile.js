var gulp = require('gulp');
var uglify = require("gulp-uglify");
var concat = require("gulp-concat");
var watch = require('gulp-watch');
 
var jsFilesToMove = [
  'bower_components/angular/angular.min.js',
  'bower_components/angular-cookies/angular-cookies.min.js',
  'bower_components/angular-route/angular-route.min.js',
  'bower_components/jquery/dist/jquery.min.js',
  'bower_components/materialize/dist/js/materialize.min.js',
  'bower_components/moment/min/moment-with-locales.min.js'
];
 
var cssFilesToMove = [
  'bower_components/materialize/dist/css/materialize.min.css'
];
 
var fontsFilesToMove = [
  'bower_components/materialize/dist/fonts/**/*'
];

var jsToMinify = [
  'bower_components/angular/angular.min.js',
  'bower_components/angular-cookies/angular-cookies.min.js',
  'bower_components/angular-route/angular-route.min.js',
  'bower_components/jquery/dist/jquery.min.js',
  'bower_components/materialize/dist/js/materialize.min.js',
  'bower_components/moment/min/moment-with-locales.min.js',
  'src/main/resources/static/app/js/angular-main.js',
  'src/main/resources/static/app/js/services/session-service.js',
  'src/main/resources/static/app/js/services/auth-service.js',
  'src/main/resources/static/app/js/services/topics-service.js',
  'src/main/resources/static/app/js/services/posts-service.js',
  'src/main/resources/static/app/js/controllers/login-controller.js',
  'src/main/resources/static/app/js/controllers/topics-controller.js',
  'src/main/resources/static/app/js/controllers/posts-controller.js'
];

gulp.task('assets-dist', function() {
    gulp.src(jsFilesToMove)
        .pipe(gulp.dest('src/main/resources/static/assets/components/js'));
    gulp.src(cssFilesToMove)
        .pipe(gulp.dest('src/main/resources/static/assets/components/css'));
    gulp.src(fontsFilesToMove)
        .pipe(gulp.dest('src/main/resources/static/assets/components/fonts'));
});

gulp.task('minify-js', function () {
  gulp.src(jsToMinify)
    .pipe(concat('recrutamento.min.js'))
    .pipe(uglify({ mangle: false }))
    .pipe(gulp.dest('src/main/resources/static/app/js'));
});
 
gulp.task('default', [ 'assets-dist', 'minify-js' ]);

gulp.task('watch', function() {
  gulp.watch(css, ['minify-js', 'minify-css']);
});