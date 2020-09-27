var URL = "http://localhost:8080/acc";

var rulesList;
var rulesOperations;
var rulesCondition;
var value;
var operation;
var condition;
var createRule;

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
	rulesList = $("#rulesList");
	rulesOperation = $("#rulesOperation");
	rulesCondition = $("#rulesCondition");
	create = $("#createRule");
	value = $("#value");
	operation = $("#operation");
	condition = $("#condition");
	updateButton = $("#updateButton");
	
	var folderId = window.location.href.split("=")[1];
	getRules(folderId);



	
	rulesList .on("click", ".deleteButton", function(){
		
		var id = $(this).data("ruleid");
		
		var data = {
				id : id			
		}
		
		$.ajax({
			url: URL + "/" + accountIndex + "/folders/" + folderId + "/rules",
			type: "DELETE",
			headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				alert("Deleted!");
			}
		});
		
	});

	
	
});


function getRules(folderId){
	$.ajax({
		url: URL + "/" + accountIndex + "/folders/" + folderId + "/rules",
		type: "GET",
		headers: {"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
		success: function(rules){
			create.append("<a href= 'createRule.html?folderId=" + folderId + "'>Add rule</a>");
			for(rule of rules){				
				rulesList.append("<input type='text' value = '" + rule.value + "'><input type='text' value = '" + rule.operation + "'><input type='text' value = '" + rule.condition + "'>");
				
				rulesList.append("<button class='deleteButton' data-ruleid = '" + rule.id + "'>X</button>");
				rulesList.append("<a href= 'updateRule.html?ruleId=" + rule.id + "&folderId=" + folderId + "'>update</a><br>")
			}
		}
	});
}
