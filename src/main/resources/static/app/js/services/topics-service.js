app.service('TopicsService', function($http, AuthService, BASE_URL) {
  var topicsService = {};

  topicsService.save = function(post) {
    return $http({
      method: 'POST',
      url: BASE_URL + "topicos",
      headers: { "Authorization": "Bearer " + AuthService.getToken() },
      data: post
    }).then(function success(response) {
      return response.data;
    }, function error(data) {
      return {};
    });
  };

  topicsService.findOne = function(id) {
    return $http({
      method: 'GET',
      url: BASE_URL + "topicos/" + id
    }).then(function success(response) {
      return response.data;
    }, function error(data) {
      return {};
    });
  };

  topicsService.findAll = function() {
    return $http({
      method: 'GET',
      url: BASE_URL + "topicos/todos"
    }).then(function success(response) {
      return response.data;
    }, function error(data) {
      return [];
    });
  };

  topicsService.delete = function (id) {
    return $http({
      method: 'DELETE',
      url: BASE_URL + "topicos/" + id,
      headers: {"Authorization": 'Bearer ' + AuthService.getToken()}
    }).then(function success(response) {
      return response.data;
    }, function error(data) {
      return {};
    });
  };

  return topicsService;
});