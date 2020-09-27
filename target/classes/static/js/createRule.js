var URL = "http://localhost:8080/acc";

var value;
var operation;
var condition;

var rule;

var create;

var accountIndex = localStorage.getItem("MvsAccount");

$(document).ready(function(){
	
var back = $("#backFolder");
	
	$("#backFolder").click(function(event){
		
		
		window.location='folder.html';
		
		  
	});
	var logoutButton=$("#logoutButton");
	logoutButton.click(function(){
		localStorage.removeItem("MvsToken");
		localStorage.removeItem("MvsAccount");
		window.location.replace("login.html")
	})
	value = $("#value");
	operation = $("#operation");
	condition = $("#condition");
	create = $("#create");
	
	var folderId = window.location.href.split("=")[1];

	createRule(folderId);
});

function createRule(folderId){
	
	create.click(function(){
		
		var data = {
				
				value : value.val(),
				operation : operation.val(),
				condition : condition.val()
		}
		
		console.log(operation.val());
		
		$.ajax({
			url : URL + "/" + accountIndex  + "/folders/" + folderId + "/rules",
			type : "POST",
			headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(){
				alert("Added rule!");
			}
		});
	});
	
}