$('document').ready(init);

function init(){
  document.getElementById('login').style.display = 'none';

  $("#todo, #inprogress, #done").sortable({

    items: "li:not(.section-title)",

    connectWith: ".connectedSortable",

    receive: function( event, ui ) {
      console.log(event.target.getAttribute("id"));
      console.log(event.originalEvent.target.dataset["id"]);
    }

  }).disableSelection();

  $(".task-title").bind("click", function(event) {
    console.log(event.originalEvent);
    alert("Task " + event.originalEvent.target.dataset["id"]);

    event.preventDefault();
  });

  $("#add-task-link").bind("click", function(event) {
    alert("Add new task " + event);

    event.preventDefault();
  });

  $(".username-link").bind("click", function(event) {
    console.log(event.originalEvent);
    alert("Username " + event.originalEvent.target.text);

    event.preventDefault();
  });

  $("#logout-link").bind("click", function(event) {
    alert("Logout ");

    event.preventDefault();
  });

  $("#login-link").bind("click", function(event) {
    console.log('i log');
    loginUtils.fadeIn();

    event.preventDefault();
  });

  $("#register-link").bind("click", function(event) {
    alert("Register ");
    
    event.preventDefault();
  });
}