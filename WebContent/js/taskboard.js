$('document').ready(init);

function reloadTasks() {
	var tasks = null;
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

function init() {
	document.getElementById('login').style.display = 'none';
	document.getElementById('register').style.display = 'none';
	
	var dialog, form;
	reloadTasks();

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
		alert("Task " + event.originalEvent.target.dataset["id"]);

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
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			processData : false,
			cache : false,
			type : "POST",
			dataType : "text",
			data : JSON.stringify(dummy),
			url : "rest/Task/Add",
		}).done(function(data, status, jqXHR) {
			console.log("Done", jqXHR);
		}).fail(function(jqXHR, status, error) {
			console.log(jqXHR);
			console.log("Fail", jqXHR.responseText);
		});

		reloadTasks();
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
		loginUtils.fadeIn('login');

		event.preventDefault();
	});

	$(document).on("click", "#register-link", function(event) {
		loginUtils.fadeIn('register')

		event.preventDefault();
	});
}