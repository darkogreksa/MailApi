var URL = "http://localhost:8080/acc";

var value;
var operation;
var condition;

var rule;

var updateButton;

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
	updateButton = $("#updateButton");
	
	var ruleId = window.location.href.split("=")[1].split("&")[0];
	var folder = window.location.href.split("&")[1];
	var folderId = folder.split("=")[1];

	getRule(ruleId, folderId);

 	updateButton.click(function(){
		
			
			var data = {
					id : ruleId,
					operation : operation.val(), 
					condition : operation.val(),
					value : value.val()
			}
			
			$.ajax({
				url: URL + "/" + accountIndex + "/folders/" + folderId + "/rules",
				type : "PUT",
				headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(){
					alert("Updated!");
					location.reload();					
				}
			});
 	});
});

function getRule(ruleId, folderId){
	
	$.ajax({
		url: URL + "/" + accountIndex + "/folders/" + folderId + "/rules/" + ruleId,
		type: "GET",
		headers: {"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
		success: function(ru){
			
			rule=ru;
			
			value.val(rule.value),
			operation.val(rule.operation),
			condition.val(rule.condition)
			console.log(rule);
		}
		});
}