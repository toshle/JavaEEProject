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
						+ "<p class=\"task-description\">"
						+ task["description"]
						+ "</p>"
						+ "<p class=\"task-executor\">"
						+ "  Deadline: "
						+ task["finalDate"]
						+ "  | Executor: <a class=\"username-link\" href=\"#\">"
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

function init() {
	document.getElementById('login').style.display = 'none';

	reloadTasks();

	$("#todo, #inprogress, #done").sortable({

		items : "li:not(.section-title)",

		connectWith : ".connectedSortable",

		receive : function(event, ui) {
			console.log(event.target.getAttribute("id"));
			console.log(event.originalEvent.target.dataset["id"]);
		}

	}).disableSelection();

	$(document).on("click", ".task-title", function(event) {
		console.log(event.originalEvent);
		alert("Task " + event.originalEvent.target.dataset["id"]);

		event.preventDefault();
	});

	$(document).on("click", "#add-task-link", function(event) {
		alert("Add new task " + event);

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
		console.log("dummy", JSON.stringify(dummy));

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
			data : JSON.stringify(dummy),
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
		console.log('i log');
		loginUtils.fadeIn();

		event.preventDefault();
	});

	$(document).on("click", "#register-link", function(event) {
		alert("Register ");

		event.preventDefault();
	});
}