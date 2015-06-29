$('document').ready(init);
var tasks = null;
function reloadTasks() {
	$("#todo, #inprogress, #done").html("");
	if ($("#todo, #inprogress, #done").is(':empty')){
		$("#todo").html("<li class=\"section-title\">Todo <a id=\"add-task-link\" href=\"#\">+</a></li>");
		$("#inprogress").html("<li class=\"section-title\">In progress</li>");
		$("#done").html("<li class=\"section-title\">Done</li>");
	}
	$.ajax({
		accept : 'application/json',
		dataType : 'text',
		success : function(data, textStatus, xhr) {
			console.log(data);
			tasks = JSON.parse(data)['task'];
			console.log(tasks);
			
			$.each(tasks, function(index, task) {
				
				$("#" + task["status"]).append(
						"<li data-id=\""
						+ task["id"]
						+ "\" class=\"ui-state-default\">"
						+ "<a class=\"task-title\" data-id=\""
						+ task["id"]
						+ "\" href=\"#\">"
						+ task["name"]
						+ "</a>"
						+ "<p data-id=\""
						+ task["id"]
						+ "\" class=\"task-description\">"
						+ task["description"]
						+ "</p>"
						+ "<p data-id=\""
						+ task["id"]
						+ "\" class=\"task-executor\">"
						+ "  Deadline: "
						+ task["finalDate"]
						+ "  | Executor: <a data-id=\""
						+ task["id"]
						+ "\" class=\"username-link\" href=\"#\">"
						+ task["executor"] + "</a>"
						+ "</p>" + "</li>"
				);

			});
			
			
		},
		error : function(xhr, textStatus, errorThrown) {
			console.log("Error.");
			console.log(xhr);
		},
		type : 'GET',
		url : "rest/Task/All"
	});
}

function addTask(form) {
	var task = {};
	
	var dummy = {
			"task" : {
				"name" : "Dummy task1",
				"description" : "Dummy description",
				"finalDate" : "2015-06-30T18:25:43.511Z",
				"status" : "todo",
				"executor" : "TheMaster723",
				"comments" : [],
				"isImportant" : false,
				"isChanged" : false
			}
		};
    
    $.each(form.elements, function(i, v) {
      var input = $(v);
      var val = input.val();
      task[input.attr("name")] = val;
      delete task["submit"];
      delete task["undefined"];
    });
    
    task["status"] = "todo";
    task["isChanged"] = false;
    task["isImportant"] = false;
    task["comments"] = [];
    
    var data = {"task": task};

	console.log("task", JSON.stringify(task));
    $.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		processData : false,
		cache : false,
		url : "rest/Task/Add",
		type : "POST",
		dataType : "text",
		data : JSON.stringify(data)
	}).done(function(data, status, jqXHR) {
		console.log("Done", jqXHR);
	}).fail(function(jqXHR, status, error) {
		console.log(jqXHR);
		console.log("Fail", jqXHR.responseText);
	});
    
	reloadTasks();
}

function retrieveTaskDetails(id) {
	var task = null;
	for (var i = 0; i < tasks.length; i++) {
        if (tasks[i]["id"] == id) {
        	task = tasks[i];
        	break;
        }  
    }
	
	var html = "<p>" + task["name"] + "</p><hr />" 
			   + "<p>" + task["description"] + "</p><br>"
			   + "<p>Executor: " + task["executor"] + "</p><br>"
			   + "<p>Final date: " + task["finalDate"] + "</p><br>";
	if(task["comments"] != undefined) {
		html += "<p>Comments</p><hr />";
		html += "<div id=\"comments\">";
		$.each(task["comments"], function(index, comment) {
			html += comment["author"] + ": " + comment["content"];
		});
		html += "</div>";
	}
	
			   
	
	$("#task-details").html(html);
	
}

function init() {
	document.getElementById('login').style.display = 'none';
	var dialog, form;
	reloadTasks();
	
	var taskDialog = $("#task-details").dialog({
		autoOpen : false,
		height : 300,
		width : 450,
		modal : true,
		buttons : {
			"Close" : function() {
				taskDialog.dialog("close");
			}
		}
	});

	$("#todo, #inprogress, #done").sortable({

		items : "li:not(.section-title)",

		connectWith : ".connectedSortable",

		receive : function(event, ui) {

			console.log(event.target.getAttribute("id"));
			console.log(event.originalEvent.target.dataset["id"]);
			var data = {
				"id" : event.originalEvent.target.dataset["id"],
				"status" : event.target.getAttribute("id")
			};
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				processData : false,
				cache : false,
				url : "rest/Task/Update",
				type : "POST",
				dataType : "text",
				data : JSON.stringify(data)
			}).done(function(data, status, jqXHR) {
				console.log("Done", jqXHR);
			}).fail(function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log("Fail", jqXHR.responseText);
			});
		}

	}).disableSelection();

	$(document).on("click", ".task-title", function(event) {
		console.log(event.originalEvent);
		retrieveTaskDetails(event.originalEvent.target.dataset["id"]);
		taskDialog.dialog( "open" );
		event.preventDefault();
	});
	
	
	

	dialog = $("#dialog-task-form").dialog({
		autoOpen : false,
		height : 300,
		width : 450,
		modal : true,
		buttons : {
			"Add task" : function() {
				addTask(form[0]);
			},
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			form[0].reset();
		}
	});
	
	$( "#finalDate" ).datepicker({
		dateFormat: "yy-mm-ddT00:00:00.000+03:00"
	});
	
	
	form = dialog.find("form").on("submit", function(event) {
		event.preventDefault();
		addTask(this);
	});
	
	$(document).on("click", "#add-task-link", function(event) {
		dialog.dialog( "open" );
		
		event.preventDefault();
	});

	$(document).on("click", ".username-link", function(event) {
		console.log(event.originalEvent);
		alert("Username " + event.originalEvent.target.text);

		event.preventDefault();
	});

	$(document).on("click", "#logout-link", function(event) {
		alert("Logout ");

		event.preventDefault();
	});

	$(document).on("click", "#login-link", function(event) {
		console.log('i log');
		loginUtils.fadeIn();

		event.preventDefault();
	});

	$(document).on("click", "#register-link", function(event) {
		alert("Register ");

		event.preventDefault();
	});
}