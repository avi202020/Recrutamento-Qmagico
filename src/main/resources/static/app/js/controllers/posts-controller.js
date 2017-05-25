app.controller('postsController', function($scope, $routeParams, PostsService, TopicsService) {
  
  $('#deletePostModal').modal()
  
  $scope.save = function() {
    $scope.post['topico'] = {"id": $routeParams.id};

    PostsService
      .save($scope.post)
        .then(function(response) {
          $scope.findAll();
          Materialize.toast("Post feito com sucesso!", 3000);
        }, function error() {

        });
  };

  $scope.loadTopic = function() {
    TopicsService
      .findOne($routeParams.id)
        .then(function success(response) {
          $scope.topic = response;
        }, function error() {
          Materialize.toast("Erro ao recuperar o tópico", 5000);
        })
  };

  $scope.findAll = function() {
    PostsService
      .findAll($routeParams.id)
        .then(function success(response) {
          $scope.posts = response;

          for(var i in $scope.posts) {
            var post = $scope.posts[i];
            post.data = moment(post.data).fromNow();
          }

        }, function error() {
          Materialize.toast("Erro ao recuperar os posts", 5000);
        });
  };

  $scope.delete = function() {
    if($scope.selectedPost != undefined) {
      PostsService
      .delete($scope.selectedPost)
        .then(function success(response) {
          $scope.findAll();
          Materialize.toast("Post apagado com sucesso", 3000);
        }, function error() {
          Materialize.toast("Erro ao apagar post", 5000);
        });
    }
  };

  $scope.selectPost = function(id) {
    $scope.selectedPost = id;
  };

  $scope.loadTopic();
  $scope.findAll();

});