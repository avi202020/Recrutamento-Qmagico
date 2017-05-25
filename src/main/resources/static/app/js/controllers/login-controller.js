app.controller("loginController", function($scope, $cookies, $location, AuthService) {
  $scope.credential = {"username": "", "password": "", "grant_type": "password"};

  $scope.login = function() {
    $scope.loading = true;

    AuthService
      .login($scope.credential)
        .then(function success(data) {
          $scope.loading = false;
        }, function error(data) {
          $scope.error = "Nome de usuário ou senha inválidos";
          $scope.loading = false;
        });
  };
 
  $scope.isAuthenticated = function() {
    return AuthService.isAuthenticated();
  };

  $scope.getUsername = function() {
    return AuthService.getUsername();
  };

  $scope.getAvatar = function() {
    return AuthService.getAvatar();
  };

  $scope.isAdmin = function() {
    return AuthService.isAdmin();
  };
 
  $scope.logout = function() {
    AuthService
      .logout()
        .then(function success() {
          AuthService.logout();
        });
  };
 
});