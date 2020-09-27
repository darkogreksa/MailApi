// API url
var URL = "http://localhost:8080/acc";

var navigation;
var messagesList;
var syncButton;
var logoutButton;

var sorting;
var ascDesc;
var sortButton;

var search;
var searchInput;
var searchButton;
var reload;

var create;
var contacts;
var folders;
var back;

var message;


var accountIndex = localStorage.getItem("MvsAccount");

$(document).ready(function(){
	create = $("#create");
	contacts = $("#contacts");
	folders = $("#folders");
	back = $("#back");
	
	$("#create").click(function(event){
		
		
		window.location='createEmail.html';
		
		  
	});
	$("#contacts").click(function(event){
		
		
		window.location='contacts.html';
		
		  
	});
	$("#folders").click(function(event){


		window.location='folder.html';


	});
	$("#searchMail").click(function(event){


    		window.location='search.html';
    	});

   	$("#searchContact").click(function(event){

       		window.location='searchContacts.html';

        });

	$("#back").click(function(event){
		
		
		window.location='messages.html';
		
		  
	});
	
	navigation = $("#navigation");
	messagesList = $("#messages");
	syncButton = $("#sync");
	logoutButton = $("#logoutButton");
	
	sorting = $("#sorting");
	ascDesc = $("#ascDesc");
	sortButton = $("#sort");
	
	search = $("#search");
	searchInput = $("#searchInput");
	searchButton = $("#searchButton");
	reloadButton = $("#reload");
	
	
	var messageId = window.location.href.split("=")[1];
	
	
	messagesList.on("click", ".deleteButton", function(){
		var id = $(this).data("messageid");
		
		$.ajax({
			url: URL + "/" + accountIndex + "/messages/" + id,
			type: "DELETE",
			headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			success: function() {
				alert("Deleted!");
			}
		});
	});
	
	logoutButton.click(function(){
		localStorage.removeItem("MvsToken");
		localStorage.removeItem("MvsAccount");
		window.location.replace("login.html")
	})
	
	getMessages();

	syncButton.click(function() {
		$.ajax({
			url: URL + "/" + accountIndex + "/messages/sync",
			type: "POST",
			headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
			success: function(){
				messagesList.empty();
				getMessages();
				alert("Sync complete");
			}
		});
	});
	getSortMessages();
	getSearchedMessage();
	getReloadMessages();
});

function getMessages(){
	
	$.ajax({
		url: URL + "/" + accountIndex + "/messages",
		type: "GET",
		headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
		success: function(messages){
			
			for(message of messages){
				
				if(message.unread){
					messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'><b>" + message.from.email + "</b></a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");
				}else{
					messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'>" + message.from.email + "</a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");	
				}				
			}
		}
	});
}

function getReloadMessages(){
	
	reloadButton.click(function(){
		$.ajax({
			url: URL + "/" + accountIndex + "/messages",
			type: "GET",
			headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
			success: function(messages){
				messagesList.empty();

				for(message of messages){
					
					if(message.unread){
						messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'><b>" + message.from.email + "</b></a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");
					}else{
						messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'>" + message.from.email + "</a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");	
					}				
				}
			}
		})
	})
}

function getSortMessages(){
	sortButton.click(function(){
		
		var data = {
			sorting : sorting.val(),
			ascDesc : ascDesc.val()
		}
		console.log(data);
	$.ajax({
		url:URL + "/" + accountIndex + "/messages/sorting?sortBy=" + sorting.val() + "&asc=" + ascDesc.val(),
		type: "GET",
		headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
		success: function(messages){
			messagesList.empty();
			for(message of messages){
				
				if(message.unread){
					messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'><b>" + message.from.email + "</b></a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");
				}else{
					messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'>" + message.from.email + "</a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");	
				}				
			}
		}
	});
});
}

function getSearchedMessage(){
	
	searchButton.click(function(){
		
		var data = {
				searchInput : searchInput.val(),				
			}
		console.log(data);
		
		$.ajax({
			url: URL + "/" + accountIndex + "/messages/search?userEmail=" + searchInput.val(),
			type: "GET",
			headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
			success: function(messages){
				messagesList.empty();
				for(message of messages){
					
					if(message.unread){
						messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'><b>" + message.from.email + "</b></a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");
					}else{
						messagesList.append("<a href='email.html?id=" + message.id + "'class='mess'>" + message.from.email + "</a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");	
					}				
				}
		}
	});
});
}