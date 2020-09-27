// API url
var URL = "http://localhost:8080/acc";

// Page components
var title;
var folderName;
var confirmButton;
var deleteButton;

var FOLDER;


var accountIndex = localStorage.getItem("MvsAccount");

$(document).ready(function() {
var back = $("#backFolder1");
	
	$("#backFolder1").click(function(event){
		
		
		window.location='folder.html';
		
		  
	});
	var logoutButton=$("#logoutButton");
	logoutButton.click(function(){
		localStorage.removeItem("MvsToken");
		localStorage.removeItem("MvsAccount");
		window.location.replace("login.html")
	})

	// Initializing page components
	title = $("h1");
	folderName = $("input[name=folder_name]");
	confirmButton = $("#confirm_button");
	deleteButton = $("#delete_button");

	// Resolving if user wants to edit or create and account
	var folderId = window.location.href.split("=")[1];

	if (folderId == null) {
		title.text("Create Folder");
		createFolder();
	} else {
		title.text("Edit Folder");
		editFolder(folderId);
	}

});

// Obtains account info from API and shows it in the fields
function editFolder(folderId) {

	confirmButton.text("Save changes");

	// Obtain account information from API
	$.ajax({
		url: URL + "/" + accountIndex + "/folders/" + folderId,
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(folder) {
			// Setting global variable and field data
			FOLDER = folder;
			folderName.val(FOLDER.name);

			// Adding handler for confirmation button
			confirmButton.click(function() {

				var data = {
					id: FOLDER.id,
					name: folderName.val()
				}

				$.ajax({
					url: URL + "/" + accountIndex + "/folders",
					type: "PUT",
					//headers: {"Authorization": "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjaG9jbWFpbGJhY2tlbmQiLCJzdWIiOiJuZW1hbmphIiwiYXVkIjoiY2hvY3VzZXIiLCJpYXQiOjE1NjA5NTQ1ODAsImV4cCI6MTU2MDk1NzU4MH0.iJuq1_td8u38dD4rnfite18RSBu4UNKUxjoN0kOPa-YBjZIOBSi9_Ld8bNfLS_1kv439_ySrFjq5PmesYltp8w"},
					headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function() {
						location.replace("createFolder.html?id=" + FOLDER.id);
					}
				});
			});

			// Adding handler for delete button
			deleteButton.click(function() {

				$.ajax({
					url: URL + "/" + accountIndex + "/folders/" + FOLDER.id,
					type: "DELETE",
					//headers: {"Authorization": "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjaG9jbWFpbGJhY2tlbmQiLCJzdWIiOiJuZW1hbmphIiwiYXVkIjoiY2hvY3VzZXIiLCJpYXQiOjE1NjA5NTQ1ODAsImV4cCI6MTU2MDk1NzU4MH0.iJuq1_td8u38dD4rnfite18RSBu4UNKUxjoN0kOPa-YBjZIOBSi9_Ld8bNfLS_1kv439_ySrFjq5PmesYltp8w"},
					headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
					success: function() {
						location.assign("folder.html");
					}
				});
			});

		}
	});


}

// Prepares fields for account creation
function createFolder() {

	confirmButton.text("Add folder");
	deleteButton.remove();

	confirmButton.click(function() {

		var data = {
			name: folderName.val()
		}

		$.ajax({
			url: URL + "/" + accountIndex + "/folders",
			type: "POST",
			//headers: {"Authorization": "Bearer " + "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJjaG9jbWFpbGJhY2tlbmQiLCJzdWIiOiJuZW1hbmphIiwiYXVkIjoiY2hvY3VzZXIiLCJpYXQiOjE1NjA5NTQ1ODAsImV4cCI6MTU2MDk1NzU4MH0.iJuq1_td8u38dD4rnfite18RSBu4UNKUxjoN0kOPa-YBjZIOBSi9_Ld8bNfLS_1kv439_ySrFjq5PmesYltp8w"},
			headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				location.assign("folder.html");
			}
		});
	});
}