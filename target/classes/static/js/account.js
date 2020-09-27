// API url
var URL = "http://localhost:8080/acc";

// Page components
var username;
var password;
var displayName;
var smtpAddress;
var smtpPort;
var imapAddress;
var imapType;
var imapPort;
var confirmButton;
var deleteButton;

var ACCOUNT;

$(document).ready(function() {
	$(document).ready(function() {
		var back = $("#backFolder");
		
		$("#backFolder").click(function(event){
			
			
			window.location='messages.html';
			
			  
		});
		var logoutButton=$("#logoutButton");
		logoutButton.click(function(){
			localStorage.removeItem("MvsToken");
			localStorage.removeItem("MvsAccount");
			window.location.replace("login.html")
		})
	// Initializing page components
	username = $("input[name=username]");
	password = $("input[name=password]");
	displayName = $("input[name=display_name]");
	smtpAddress = $("input[name=smtp_address]");
	smtpPort = $("input[name=smtp_port]");
	imapAddress = $("input[name=imap_address]");
	imapType = $("input[name=imap_type]");
	imapPort = $("input[name=imap_port]");
	confirmButton = $("#confirm_button");
	deleteButton = $("#delete_button");

	// Resolving if user wants to edit or create and account
	var accountId = window.location.href.split("=")[1];

	if (accountId == null) {
		createAccount();
	} else {
		editAccount(accountId);
	}

});

// Obtains account info from API and shows it in the fields
function editAccount(accountId) {

	confirmButton.text("Save changes");

	// Obtain account information from API
	$.ajax({
		url: URL + "/" + accountId,
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(account) {
			// Setting global variable and field data
			ACCOUNT = account;
			username.val(ACCOUNT.username),
			password.val(ACCOUNT.password),
			displayName.val(ACCOUNT.displayName),
			smtpAddress.val(ACCOUNT.smtpAddress),
			smtpPort.val(ACCOUNT.smtpPort),
			imapAddress.val(ACCOUNT.inServerAddress),
			imapType.val(ACCOUNT.inServerType),
			imapPort.val(ACCOUNT.inServerPort)

			// Adding handler for confirmation button
			confirmButton.click(function() {

				
				var data = {
					id: ACCOUNT.id,
					username: username.val(),
					password: password.val(),
					displayName: displayName.val(),
					smtpAddress: smtpAddress.val(),
					smtpPort: smtpPort.val(),
					inServerAddress: imapAddress.val(),
					inServerType: imapType.val(),
					inServerPort: imapPort.val()
				}

				$.ajax({
					url: URL,
					type: "PUT",
					//headers: {"Authorization": "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjaG9jbWFpbGJhY2tlbmQiLCJzdWIiOiJuZW1hbmphIiwiYXVkIjoiY2hvY3VzZXIiLCJpYXQiOjE1NjA5NTQ1ODAsImV4cCI6MTU2MDk1NzU4MH0.iJuq1_td8u38dD4rnfite18RSBu4UNKUxjoN0kOPa-YBjZIOBSi9_Ld8bNfLS_1kv439_ySrFjq5PmesYltp8w"},
					headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function() {
						alert("Radi");
					}
				});
			});

			// Adding handler for delete button
			deleteButton.click(function() {

				var data = {
					id: ACCOUNT.id
				}

				$.ajax({
					url: URL,
					type: "DELETE",
					//headers: {"Authorization": "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjaG9jbWFpbGJhY2tlbmQiLCJzdWIiOiJuZW1hbmphIiwiYXVkIjoiY2hvY3VzZXIiLCJpYXQiOjE1NjA5NTQ1ODAsImV4cCI6MTU2MDk1NzU4MH0.iJuq1_td8u38dD4rnfite18RSBu4UNKUxjoN0kOPa-YBjZIOBSi9_Ld8bNfLS_1kv439_ySrFjq5PmesYltp8w"},
					headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function() {
						alert("Radi");
					}
				});
			});

		}
	});


}

// Prepares fields for account creation
function createAccount() {

	confirmButton.text("Add account");
	deleteButton.remove();

	confirmButton.click(function() {

		var data = {
			username: username.val(),
			password: password.val(),
			displayName: displayName.val(),
			smtpAddress: smtpAddress.val(),
			smtpPort: smtpPort.val(),
			inServerAddress: imapAddress.val(),
			inServerType: imapType.val(),
			inServerPort: imapPort.val()
		}

		$.ajax({
			url: URL,
			type: "POST",
			//headers: {"Authorization": "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjaG9jbWFpbGJhY2tlbmQiLCJzdWIiOiJuZW1hbmphIiwiYXVkIjoiY2hvY3VzZXIiLCJpYXQiOjE1NjA5NTQ1ODAsImV4cCI6MTU2MDk1NzU4MH0.iJuq1_td8u38dD4rnfite18RSBu4UNKUxjoN0kOPa-YBjZIOBSi9_Ld8bNfLS_1kv439_ySrFjq5PmesYltp8w"},
			headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				alert("Radi");
			}
		});
	});
}