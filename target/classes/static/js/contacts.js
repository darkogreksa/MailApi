var URL = "http://localhost:8080/contacts";

var firstName;
var lastName;
var displayName;
var email;
var note;
var delete_contact;
var confirmButton;
var CONTACT;



$(document).ready(function() {
	
	
	firstName = $("input[name=firstNameInput]");
	lastName = $("input[name=lastNameInput]");
	displayName = $("input[name=displayNameInput]");
	email = $("input[name=emailInput]");
	note = $("input[name=noteInput]");
	confirmButton = $("#confirm_button_contact");
	delete_contact =  $("#delete_contact");
	
	var contactId = window.location.href.split("=")[1];
	
	
	
	if(contactId == null) {
		createContact();
	}else {
		editContact(contactId);
	}
	
	
var adminTabela = $('#adminTabela'); 
	
	$.ajax({
        url: "http://localhost:8080/contacts", 
        type:"GET",
        headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
        data : 'json',
	    contentType: "application/json; charset=UTF-8"

         ,success: function(res){
        	 console.log(res);
            var index = 0;
            var subObj = '';
            var htm = '';
            adminTabela.append('<tbody>');
            for(index = 0; index < res.length; index++) {
            	subObj    =   res[index];
            	adminTabela.append(
						'<tr class=\'clickable-row\' data-href=\'url://\'>' + 
							
							'<td>' + subObj.displayName + '</td>' + 
							'<td>' + subObj.email + '</td>' + 
							
							'<td><button id="edit">Edit</button></td>' + 
							'<td><button id="delete">Delete</button></td>' + 
						'</tr>'
					)
					$("#edit").on('click',function(event) {
		            	console.log(subObj.id)
		            	$.ajax({
						        url: URL + "/"+subObj.id, 
						        type:"GET",
						        headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")}
						        
						
						         ,success: function(res){
						        	 CONTACT = res;
						 			firstName.val(CONTACT.firstName);
						 			lastName.val(CONTACT.lastName);
						 			displayName.val(CONTACT.displayName);
						 			email.val(CONTACT.email);
						 			note.val(CONTACT.note);
						 			
						 			
						 			confirmButton.click(function() {

						 				
						 				
						 				
						 				var data = {
						 					id: CONTACT.id,
						 					firstName : firstName.val(),
						 					lastName : lastName.val(),
						 					displayName: displayName.val(),
						 					email : email.val(),
						 					note : note.val()
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
						        	 
		            
					},error:function(resp){


			          }
		          });
		            	
		            	
		            	
		            });

          }
            adminTabela.append('</tbody>');
            


         },error:function(resp){


          }

    });
	
});


function editContact(contactId) {
	
	confirmButton.text("Save changes");
	
	$.ajax({
		url: URL + "/" + contactId,
		type: "GET",
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(contact) {
			
			CONTACT = contact;
			firstName.val(CONTACT.firstName);
			lastName.val(CONTACT.lastName);
			displayName.val(CONTACT.displayName);
			email.val(CONTACT.email);
			note.val(CONTACT.note);
			
			
			confirmButton.click(function() {

				
				
				
				var data = {
					id: CONTACT.id,
					firstName : firstName.val(),
					lastName : lastName.val(),
					displayName: displayName.val(),
					email : email.val(),
					note : note.val()
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

			
			delete_contact.click(function() {

			

				
				$.ajax({
					url: URL + "/" + CONTACT.id,
					type: "DELETE",
					headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
					success: function() {
						alert("Radi");
					}
				});
				
				
				
			});

		}
	});

	
	
	
	
}

function getContacts(){
	
	$.ajax({
		url: URL + "/" + accountIndex + "/contacts",
		type: "GET",
		headers:{"Authorization" : "Bearer " + localStorage.getItem("MvsToken")},
		success: function(contacts){
			
			for(contact of contacts){
				
				
			contactsList.append("<a href='contacts.html?id=" + contact.id + "'class='mess'>" + message.from.email + "</a><button class='deleteButton' data-messageid = '"+ message.id + "'>X</button><br><dd>" + message.subject + "</dd>");	
								
			}
		}
	});
}

function createContact() {

	confirmButton.text("Add contact");
	delete_contact.remove();

	confirmButton.click(function() {

		
		var data= {
				firstName: firstName.val(),
				lastName: lastName.val(),
				displayName: displayName.val(),
				email : email.val(),
				note : note.val()
				
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