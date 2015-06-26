var loginUtils = {
  isValidEmail: function(email) {
		var emailRegularExpression = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return emailRegularExpression.test(email);
	},
  
  fadeIn: function() {
    var opacity = 0;
    var loginDiv = document.getElementById('login');
    loginDiv.style.display = 'block';
    loginDiv.style.opacity = 0;
    
    var timer = setInterval(function() {
      if(opacity >= 1.1) clearInterval(timer);
      loginDiv.style.opacity = opacity;
      loginDiv.style.filter = 'alpha(opacity=' + opacity * 100 + ")";
      opacity += 0.15;
    }, 80);
  },
  
  fadeOut: function() {
    var opacity = 1;
    var loginDiv = document.getElementById('login');
    loginDiv.style.opacity = 1;
    
    var timer = setInterval(function() {
      if(opacity <= 0.1) { loginDiv.style.display = 'none'; clearInterval(timer); }
      loginDiv.style.opacity = opacity;
      loginDiv.style.filter = 'alpha(opacity=' + opacity * 100 + ")";
      opacity -= 0.15;
    }, 80);
  },
  
  loginAndFadeOut: function(elementID, newText) {
		var paragraph = document.getElementById(elementID),
			opacity = 1;
		
		if(paragraph.innerHTML.length < 10 || elementID === 'login') {
			if(elementID !== 'login') paragraph.innerHTML = newText;
		    var timer = setInterval(function() {
		        if(opacity <= 0.1) {
		            clearInterval(timer);
		            if(newText === "Logged in.") presentationUtils.loginAndFadeOut('login', 'Logging in.');
		            else paragraph.innerHTML = '&nbsp;';
		        }
		        paragraph.style.opacity = opacity;
		        paragraph.style.filter = 'alpha(opacity=' + opacity * 100 + ")";
		        opacity -= opacity * 0.1;
		    }, 80);
		}
	},

	executeQuery: function(parameters) {
		$.ajax({
	        url: './services/gameQueries.js?' + parameters,
	        success: function(response) {
	        	presentationUtils.loginAndFadeOut('exceptionText', response.toString());
	        },
			error: function(response) {
				alert(response.responseText);
			}
	  });
	},

	registerUser: function() {
		var email 	 = document.getElementById('email').value,
			password = document.getElementById('password').value,
			request  = 'cmd=registerUser&email=' + email + '&password=' + password,
      exceptionMessage = 'Please fill the fields.';
      
		if(email && password) {
      if(!this.isValidEmail(email)) {
        exceptionMessage = 'Please enter a valid email.';
        this.loginAndFadeOut('exceptionText', exceptionMessage);
      }
      else this.executeQuery(request);
    }
    else this.loginAndFadeOut('exceptionText', exceptionMessage);
	}
};