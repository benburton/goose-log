/**
 * This is terrible Gustavo! Fix it!
 */
(function() {
  var s = document.createElement('script');
  s.type = 'text/javascript';
  s.async = true;
  s.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js';
  var x = document.getElementsByTagName('script')[0];
  x.parentNode.insertBefore(s, x);
})();

/**
 * Oh man! This is worse!
 */
var gooseLog = function(data) {
  $.ajax({
    type: "POST",
    contentType: "application/json",
    url: "http://localhost:8080/",
    data: JSON.stringify(data),
    success: function(data) {
      alert("success!");
    },
    error: function(data) {
      alert("Gustavo noooo!");
    }
  });
};