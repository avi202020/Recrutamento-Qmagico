app.service('AuthService', function($http, Session, BASE_URL) {
  var authService = {};
 
  authService.login = function(credentials) {
    return $http({
      method: "POST",
      url: BASE_URL + "oauth/token",
      headers: { "Authorization": "Basic cmVjcnV0YW1lbnRvOnJlY3J1dGFtZW50bw==" },
      params: credentials
    })
    .then(function success(response) {
      var token = response.data.access_token;

      authService
        .getUser(token)
          .then(function success(u) {
            var isAdmin = u.authorities[0] == 'ADMIN';
            var session = {"username": u["username"], "token": token, 
                           "avatar": u["avatar"], 'admin': isAdmin};
            Session.create(session);
          });
      
      return token;

    }, function error() {
      return {};
    });
  };

  authService.getUser = function (token) {
    return $http({
        method: "GET",
        url: BASE_URL + "users",
        headers: {"Authorization": "Bearer " + token}
      })
      .then(function success(response) {
        return response['data'];
      }, function error(data) {
        return {};
      });
  };
 
  authService.isAuthenticated = function() {
    if(Session.getSession() !== undefined) {
      return true;
    }
    return false;
  };

  authService.getToken = function() {
    var session = Session.getSession();
    if(session !== undefined) {
      return session['token'];
    }
  };

  authService.getUsername = function() {
    var session = Session.getSession();
    if(session !== undefined) {
      return session['username'];
    }
  };

  authService.getAvatar = function() {
    var session = Session.getSession();
    if(session !== undefined) {
      return session['avatar'];
    }
  };

  authService.isAdmin = function() {
    var session = Session.getSession();
    if(session !== undefined) {
      return session['admin'];
    }
  };
 
  authService.logout = function() {
    return $http({
      method: "GET",
      url: BASE_URL + "oauth/logout",
    }).then(function success() {
      Session.destroy();
    });
  };
 
  return authService;
 
});