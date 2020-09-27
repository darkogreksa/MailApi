// API url
var URL = "http://localhost:8080/acc";

var to;
var cc;
var bcc;
var content;
var subject;
var send;

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
	
	to = $("input[name=to]");
	cc = $("input[name=cc]");
	bcc = $("input[name=bcc]");
	content = $("input[name=content]");
	subject = $("input[name=subject]");
	send = $("#sendButton");
	console.log('DJSJDJAS');	
	createEmail();
	
});

function createEmail(){
	
	
	send.click(function(){
		
		stringCC = cc.val().split(", ");
		stringBCC = bcc.val().split(", ");
		stringTO = to.val().split(", ");
		
		listCC = [];
		listBCC = [];
		listTO = [];
		
		for(contact of stringCC){
			listCC.push({
				email: contact
			});
		}
		for(contact of stringBCC){
			listBCC.push({
				email: contact
			});
		}
		for(contact of stringTO){
			listTO.push({
				email: contact
			});
		}

		var file = document.querySelector('input[type="file"]').files[0];
		if (file != null) {
			let reader = new FileReader();
			reader.readAsDataURL(file);

			reader.onload = function () {
				var attachment = {
					name: file.name,
					data: reader.result.split(",")[1],
					mimeType: reader.result.split(";")[0].split(":")[1],
				};

				var data = {	
					to: listTO,
					cc: listCC,
					bcc: listBCC,
					subject: subject.val(),
					content: content.val(),
					tags: [],
					attachments: [attachment]
				};
				
				console.log(data);
				sendMessage(data);
			};

		} else {
			var data = {	
				to: listTO,
				cc: listCC,
				bcc: listBCC,
				subject: subject.val(),
				content: content.val(),
				tags: [],
				attachments: []
			};

			sendMessage(data);
		}
	});
}

function sendMessage(data) {

	var accountIndex = localStorage.getItem("MvsAccount");

	$.ajax({
		url : URL + "/" + accountIndex + "/messages",
		type : "POST",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(){
			alert("RADII!");
		}
	});
}

