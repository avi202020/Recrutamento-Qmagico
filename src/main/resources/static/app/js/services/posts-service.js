app.service('PostsService', function($http, AuthService, BASE_URL) {
  var postsService = {};

  postsService.save = function(post) {
    return $http({
      method: 'POST',
      url: BASE_URL + "posts",
      headers: {"Authorization": "Bearer " + AuthService.getToken() },
      data: post
    }).then(function success(response) {
      return response.data;
    }, function error() {
      return {};
    });
  };

  postsService.findOne = function(id) {
    return $http({
      method: 'GET',
      url: BASE_URL + "posts/" + id
    }).then(function success(response) {
      return response.data;
    }, function error() {
      return {};
    });
  };

  postsService.findAll = function(topic) {
    return $http({
      method: 'GET',
      url: BASE_URL + "posts/topico/" + topic
    }).then(function success(response) {
      return response.data;
    }, function error() {
      return {};
    });
  };

  postsService.delete = function (id) {
    return $http({
      method: 'DELETE',
      url: BASE_URL + "posts/" + id,
      headers: {"Authorization": "Bearer " + AuthService.getToken()}
    }).then(function success(response) {
      return response.data;
    }, function error() {
      return {};
    });
  };

  return postsService;
});