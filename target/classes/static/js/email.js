// API url
var URL = "http://localhost:8080/acc";

var container;
var from;
var cc;
var bcc;
var content;
var subject;
var send;

var MESSAGE;


var accountIndex = localStorage.getItem("MvsAccount");

$(document).ready(function(){
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
	
	container = $("#container");
	from = $("input[name=from]");
	cc = $("input[name=cc]");
	bcc = $("input[name=bcc]");
	content = $("#content");
	subject = $("input[name=subject]");

	var messageId = window.location.href.split("=")[1];
	
	getEmail(messageId);
});

function getEmail(messageId){
		
		$.ajax({
			url: URL + "/" + accountIndex + "/messages/" + messageId,
			type: "GET",
			headers:{"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
			success: function(mess){
				
				MESSAGE = mess;
				from.val(MESSAGE.from.email);
				
				ccString = "";
				for(contact of mess.cc){
					ccString += contact.email + ", ";
				}
				
				cc.val(ccString);
				
				bccString = "";
				for(contact of mess.bcc){
					bccString += contact.email + ", ";
				}
				
				bcc.val(bccString);
				subject.val(MESSAGE.subject);
				content.text(MESSAGE.content);

				for (attachment of mess.attachments) {
					container.append("<div><a href='data:" + attachment.mimeType + ";base64," + attachment.data + "' download='" + attachment.name + "'>" + attachment.name + "</a></div>");
				}

				container.append("<a href='moveMessage.html?id=" + MESSAGE.id + "'>Move to folder</a>");
			}
			
		});

		$.ajax({
			url: URL + "/" + accountIndex + "/messages/" + messageId,
			type: "PUT",
			headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			success: function(){
				console.log("Message is now read!");
			}
		});
}