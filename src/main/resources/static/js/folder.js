// API url
var URL = "http://localhost:8080/acc";

// Page components
var navigation;
var subfolders;
var messages;

var rules;

var FOLDER;


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

	// Initializing page components
	navigation = $("#navigation");
	subfolders = $("#subfolders");
	messages = $("#messages");
	rules = $("#rules");

	// Resolving if user wants to edit or create and account
	var folderId = window.location.href.split("=")[1];

	if (folderId == null) {
		$("#rules").remove();
		$("h3:eq(1)").remove();
		//$("#back").attr("href", "messages.html");
		getRootFolders();
		navigation.append("<h1><a href='createFolder.html'>Create folder</a></h1>");
	} else {
		getFolder(folderId);
		navigation.append("<a href='#' id='apply_rules'>Apply rules</a>");
		navigation.append("<a href='createFolder.html?id=" + folderId + "'>Edit folder</a>");

		rules.attr("href", "rules.html?folderid=" + folderId);
		
		navigation.on("click", "#apply_rules", function() {
			applyRules();
		});
	}



});

// Obtains info about a specific folder
function getFolder(folderId) {

	$.ajax({
		url: URL + "/" + accountIndex + "/folders/" + folderId,
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(folder) {

			FOLDER = folder;

			$("#navigation").after("<h1>" + folder.name + "</h1>");
			
			for (subfolder of folder.subFolders) {	
				subfolders.append("<a href='folder.html?id=" + subfolder.id + "' class='subfolder'>" + subfolder.name + "</a>"); 
			}

			for (message of folder.messages) {	
				messages.append("<a href='email.html?id=" + message.id + "' class='message'>" + message.subject + "</a>"); 
			}

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
				subfolders.append("<a href='folder.html?id=" + folder.id + "' class='subfolder'>" + folder.name + "</a>"); 
			}

		}
	});

}

function applyRules() {
	$.ajax({
		url: URL + "/" + accountIndex + "/folders/" + FOLDER.id + "/rules/apply",
		type: "POST",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(folders) {
			alert("Success");
			location.reload();
		}
	});
}