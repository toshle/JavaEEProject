var loginUtils = {
	isValidEmail: function(email) {
		var emailRegularExpression = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return emailRegularExpression.test(email);
	},
  
	fadeIn: function(elementID) {
	    var opacity = 0;
	    var loginDiv = document.getElementById(elementID);
	    loginDiv.style.display = 'block';
	    loginDiv.style.opacity = 0;
	    
	    var timer = setInterval(function() {
	      if (opacity >= 1.1) clearInterval(timer);
	      loginDiv.style.opacity = opacity;
	      loginDiv.style.filter = 'alpha(opacity=' + opacity * 100 + ")";
	      opacity += 0.15;
	    }, 80);
	},
  
	fadeOut: function(elementID) {
	    var opacity = 1;
	    var loginDiv = document.getElementById(elementID);
	    loginDiv.style.opacity = 1;
	    
	    var timer = setInterval(function() {
	      if (opacity <= 0.1) { loginDiv.style.display = 'none'; clearInterval(timer); }
	      loginDiv.style.opacity = opacity;
	      loginDiv.style.filter = 'alpha(opacity=' + opacity * 100 + ")";
	      opacity -= 0.15;
	    }, 80);
	},
  
	loginAndFadeOut: function(elementID, newText) {
		var paragraph = document.getElementById(elementID),
			opacity = 1;
		
		if(paragraph.innerHTML.length < 10 || elementID === 'login' || elementID === 'register') {
			if (elementID !== 'login' && elementID !== 'register') paragraph.innerHTML = newText;
		    var timer = setInterval(function() {
		        if (opacity <= 0.1) {
		            clearInterval(timer);
		            if (newText === "Logged in." || newText === 'Registered.') {
		            	if (elementID === 'exceptionText') {
		            		loginUtils.loginAndFadeOut('login', 'Logging in...');
		            	}
		            	else {
		            		loginUtils.loginAndFadeOut('register', 'Registering...');
		            	}
		            }
		            else paragraph.innerHTML = '&nbsp;';
		        }
		        paragraph.style.opacity = opacity;
		        paragraph.style.filter = 'alpha(opacity=' + opacity * 100 + ")";
		        opacity -= opacity * 0.1;
		    }, 80);
		}
	},

	executeQuery: function(userInfo, textID) {
		var loginOrRegisterService = 'Login';
		var successMessage = 'Logged in.';
		var failMessage = 'User does not exist';
		
		if (textID === 'registerText') {
			loginOrRegisterService = 'Register';
			successMessage = 'Registered.';
			failMessage = 'User already exists';
		}
		
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			processData : false,
			cache : false,
			type : "POST",
			dataType : "text",
			data : JSON.stringify(userInfo),
	        url : "rest/User/" + loginOrRegisterService,
	        

		}).done(function(data, status, jqXHR) {
			if (loginOrRegisterService === 'Login')
			{
				if (jqXHR.status === 200) {
					loginUtils.loginAndFadeOut(textID, 'Logged in.');
					
					var loggedUser = JSON.parse(jqXHR.responseText).user;
					console.log(loggedUser.email);
					console.log('Logged in with ' + JSON.stringify(loggedUser));
					// Do something with the logged user.
					var helloUserElement = document.getElementById('user-info'); 
					helloUserElement.innerHTML = 
						'Hello, <a class="username-link" data-id="1" href="#"> ' + loggedUser.fullName + '!</a>';
					
					document.getElementById('logoutLi').style.visibility = 'visible';
					document.getElementById('loginLi').style.visibility = 'hidden';
					document.getElementById('separatorLi').style.visibility = 'hidden';
					document.getElementById('registerLi').style.visibility = 'hidden';
				}
				else loginUtils.loginAndFadeOut(textID, jqXHR.responseText);
			}
			else loginUtils.loginAndFadeOut(textID, jqXHR.responseText);
		}).fail(function(jqXHR, status, error) {
			loginUtils.loginAndFadeOut(textID, jqXHR.responseText);
		});
	},

	loginOrRegister: function(elementID) {
		var email, password, userInfo, fullname = 'dummy', textID = 'exceptionText', exceptionMessage = 'Please fill the fields.';
		if (elementID === 'login') {
			email 	 = document.getElementById('email').value;
			password = document.getElementById('password').value;
		}
		else {
			textID = 'registerText';
			email 	 = document.getElementById('remail').value;
			password = document.getElementById('rpassword').value;
			fullname = document.getElementById('rfullname').value;
		}
		
		userInfo = {
			"user" : {
				"userName" : email,
				"passwd" : password,
				"fullName" : fullname,
				"email" : email,
				"isAdministrator" : false,
				"tasks": []
			}
		}
		
		if (email && password && ((textID === 'registerText' && fullname) || textID === 'exceptionText')) {
	      if (!this.isValidEmail(email)) {
	        exceptionMessage = 'Please enter a valid email.';
	        this.loginAndFadeOut(textID, exceptionMessage);
	      }
	      else {
	    	  if(fullname !== 'dummy') {
		    	  if (password.length < 6) {
		    		  exceptionMessage = 'Password should be at least 6 letters';
		    		  this.loginAndFadeOut(textID, exceptionMessage);
		    	  }
		    	  else if (fullname.length < 6) {
		    		  exceptionMessage = 'Fullname should be at least 6 letters';
		    		  this.loginAndFadeOut(textID, exceptionMessage);
		    	  } else {
		    		  this.executeQuery(userInfo, textID);
		    	  }
	    	  } else {
	    		  this.executeQuery(userInfo, textID);
	    	  }
	      }
		}
		else this.loginAndFadeOut(textID, exceptionMessage);
	},
};