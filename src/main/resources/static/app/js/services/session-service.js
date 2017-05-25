app.service('Session', function($cookies) {
  var session = {};
 
  session.create = function(data) {
    $cookies.put('session', JSON.stringify(data));
  };
 
  session.getSession = function() {
    var sessionJson = $cookies.get('session');
    if(sessionJson != undefined) {
      return JSON.parse(sessionJson);
    }
  };
 
  session.destroy = function() {
    $cookies.remove('session');
  };
 
  return session;
});