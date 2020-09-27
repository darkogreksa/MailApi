// API url
var URL = "http://localhost:8080";

var signInButton;
var email;
var password;

$(document).ready(function() {
	signInButton = $("#log");
	email = $("#email");
	password = $("#password");
	
	login();
	
	$("#register").click(function(event){
		  
		  var user = $("#reg_username").val();
		  var pass = $("#reg_password").val();
		  var last=$("#reg_lastfullname").val();
		  var name=$("#reg_fullname").val();
		  var email=$("#reg_email").val();
		  var greska2=$("#greska2");
		  var greska3=$("#greska3");
		  var greska4=$("#greska4");
		  var greska5=$("#greska5");
		  var greska6=$("#greska6");
		  if (user=="" || pass==""|| last=="" || name=="" || email=="") {
			  greska2.text('Niste uneli podatke');

				event.preventDefault();
				return false;
			}
		  if (user=="" ) {
			  greska2.text('Niste uneli podatke');

				event.preventDefault();
				return false;
			}
		  if (pass=="" ) {
			  greska3.text('Niste uneli podatke');

				event.preventDefault();
				return false;
			}
		  if (last=="" ) {
			  greska4.text('Niste uneli podatke');

				event.preventDefault();
				return false;
			}
		  if (name=="" ) {
			  greska5.text('Niste uneli podatke');

				event.preventDefault();
				return false;
			}
		  if (email=="" ) {
			  greska6.text('Niste uneli podatke');

				event.preventDefault();
				return false;
			}

		  var registerForm = {
			'firstname':name,
			'lastname':last,
			'username': user,
			'password': pass
		  };
		  
		  console.log(registerForm);
		  
		  var xhr = new XMLHttpRequest();		  
		  xhr.open("POST", "http://localhost:8080/users/registration", true);
		  xhr.setRequestHeader("Content-Type", "application/json;charset=utf-8");
		  xhr.responseType = 'json';

		  xhr.send(JSON.stringify(registerForm));
		  window.location='login.html';

		  
	  });
});

function login(){
    console.log('dasihdlasjhdjklas');
	signInButton.click(function(){
		console.log('flsajkldf00');
		var data = {
				"username" : email.val(),
				"password" : password.val()
		}
		
		$.ajax({
			url: URL + "/authenticate",
			type : "POST",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(token){
				localStorage.setItem("MvsToken", token.token);
				
				location.assign("chooseAccount.html")
			}
		})
	});
}