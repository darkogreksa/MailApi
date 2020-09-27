// API url
var URL = "http://localhost:8080/acc";

$(document).ready(function() {
	
	var logoutButton = $("#but");
	
	logoutButton.click(function(){
		localStorage.removeItem("ChocMailToken");
		localStorage.removeItem("ChocMailAccount");
		window.location.replace("login.html")
	})

	var container = $("#container");

	container.on("click", ".account", function() {
		localStorage.setItem("MvsAccount", $(this).data("index"));

		window.location.assign("messages.html");
	});

	$.ajax({
		url: URL,
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		type: "GET",
		success: function(accounts) {
			var counter = 0;
			for (account of accounts) {
				container.append("<button class='account' id='but' data-index='" + counter + "'>" + account.displayName + "</button>");
				counter++;
				
			}
			
		}
	});

});