var app = angular.module('recrutamento', ['ngCookies', 'ngRoute']);
app.constant('BASE_URL', 'http://locahost:8080/');

app.config(function ($routeProvider, $httpProvider) {
  $routeProvider
    .when("/", {
      templateUrl: 'app/partials/topicsList.html',
      controller: 'topicsController'
    })
    .when("/topic/:id", {
      templateUrl: 'app/partials/postsList.html',
      controller: 'postsController'
    });

});
