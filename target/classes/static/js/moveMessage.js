// API url
var URL = "http://localhost:8080/acc";

var container;
var messageId;


var accountIndex = localStorage.getItem("MvsAccount");

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

	messageId = window.location.href.split("=")[1];

	container = $("#container");

	container.on("click", ".folder", function() {
		moveMessageToFolder($(this).data("id"));
	});

	getRootFolders();

});


function moveMessageToFolder(folderId) {

	var data = {
		messageId: parseInt(messageId),
		folderId: parseInt(folderId)
	};

	console.log(data);

	$.ajax({
		url: URL + "/" + accountIndex + "/messages/move",
		type: "POST",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(data) {
			location.assign("messages.html");
		}
	});
}

// Obtains info about root folders
function getRootFolders() {

	$.ajax({
		url: URL + "/" + accountIndex + "/folders",
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(folders) {
			
			for (folder of folders) {	
				container.append("<button id='but' data-id='" + folder.id + "' class='folder'>" + folder.name + "</button>"); 

				for (subFolder of folder.subFolders) {
					container.append("<button id='but' data-id='" + subFolder.id + "' class='folder'>" + subFolder.name + "</button>"); 
				}
			}

		}
	});

}