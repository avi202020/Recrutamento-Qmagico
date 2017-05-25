app.controller('topicsController', function($scope, TopicsService, PostsService) {

  $("#newTopicModal").modal();
  $('#deleteTopicModal').modal();

  $scope.save = function() {
    $scope.saving = true;

    $scope.topic = {"titulo": $scope.topicDraft['title']}
    $scope.firstPost = {"conteudo": $scope.topicDraft['content']};

    TopicsService
      .save($scope.topic)
        .then(function success(response) {

          $scope.firstPost['topico'] = {'id': response};

          PostsService
            .save($scope.firstPost)
              .then(function success(response) {
                Materialize.toast('Tópico criado', 3000);
                $("#newTopicModal").modal('close');
                $scope.findAll();
                $scope.saving = false;
              }, function error() {
                Materialize.toast('Cannot connect to server', 5000);
                $scope.saving = false;
              });

        }, function error() {
          Materialize.toast('Cannot connect to server', 5000);
          $scope.saving = false;
        });
  };

  $scope.findAll = function() {
    $scope.loading = true;

    TopicsService
      .findAll()
        .then(function success(response) {
          $scope.topics = response;

          for(var i in $scope.topics) {
            var topic = $scope.topics[i];
            topic.data = moment(topic.data).fromNow();
          }

          $scope.loading = false;

        }, function error(data) {
          Materialize.toast('Cannot connect to server', 5000);
          $scope.loading = false;
        });
  };

  $scope.delete = function() {
    if($scope.selectedTopic != undefined) {
      TopicsService
        .delete($scope.selectedTopic)
          .then(function success(response) {
            $scope.findAll();
            $scope.selectedTopic = undefined;
            $('#deleteTopicModal').modal('close');
            Materialize.toast('Tópico apagado com sucesso', 3000);
          }, function error() {
            Materialize.toast('Erro ao apagar o post', 5000);
          });
    }
  }

  $scope.selectTopic = function(id) {
    $scope.selectedTopic = id;
  };

  $scope.findAll();

});