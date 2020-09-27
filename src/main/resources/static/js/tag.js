var URL = "http://localhost:8080/tags";

var tagName;
var confirmButton;
var delete_tag;
var TAG;

$(document).ready(function(){

	
	tagName = $("input[name=tagInput]");
	confirmButton = $("#confirm_tag_button");
	delete_tag =  $("#delete_tag");
	var tagId = window.location.href.split("=")[1];
	
	
	
	
	if(tagId == null) {
		createTag();
	}else {
		editTag(tagId);
	}
		
});


function editTag(tagId) {
	
	confirmButton.text("Save changes");
	
	
	
	$.ajax({
		url: URL + "/" + tagId,
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(tag) {
			
			TAG = tag;
			tagName.val(TAG.name);
		
			
			confirmButton.click(function() {

				
				
				
		
				var data = {
					id: TAG.id,
					name : tagName.val()
					
				}
				
				

				$.ajax({
					url: URL,
					type: "PUT",
					headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
					contentType: "application/json",
					data: JSON.stringify(data),
					success: function() {
						alert("Radi");
					}
				});
				
			});

			
			delete_tag.click(function() {

				

				
				var data = {
					id: TAG.id,
					name : tagName.val()
					
				}
				
				$.ajax({
					url: URL,
					type: "DELETE",
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



function createTag() {
	
	confirmButton.text("Add tag");
	
	delete_tag.remove();
	
	confirmButton.click(function() {

		
		
		var data= {
				name: tagName.val()
				
		}

		
		$.ajax({
			url: URL,
			type: "POST",
			headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function() {
				alert("Radi");
			}
			
			
		});
	});
	

}