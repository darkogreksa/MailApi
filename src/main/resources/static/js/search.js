var URL = "http://localhost:8080/";

$(document).ready(function(){
	var searchType = $('#searchType').val();
	console.log(searchType);


	$('#btnSubmitLuceneQueryLanguage').click(function(event){
		event.preventDefault();

		var type = $('#searchType').val();
		if (type == 'regluar'){
			regular();
		}
		else if (type == 'phrase'){
			phrase();
		}
		else if (type == 'fuzzy'){
			fuzzy();
		}
	});


function regular(){
console.log('clik');
	var value = $('#luceneQueryLanguage input[name=query]').val();
	var field = $('#field').val();
	var result = $('#resultTable');


	var data = {
			'value': value,
			'field': field
	}

	$.ajax({
		type: 'POST',
		url:  URL + 'searcher/search/term',
		data: JSON.stringify(data),
		contentType: 'application/json',
		headers: {"Authorization": "Bearer " + localStorage.getItem("MvsToken")},
		success: function(data){
			console.log(data);
			for (var i=0; i<data.length; i++){
                $('#resultTable').append('<tr>'
                +'<td>'+ data[i].title +'</td>'
                +'<td>'+ data[i].sender +'</td>'
                +'<td>'+ data[i].receiver +'</td>'
                +'<td>'+ data[i].text +'</td>'
                +'</tr>');
            }
		},
        error: function (e) {
        	$('#result').empty();
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSearch").prop("disabled", false);

        }
	});
}

function phrase(){
	var value = $('#luceneQueryLanguage input[name=query]').val();

	var field = $('#field').val();
	var result = $('#resultTable');

	var data = {
			'value': value,
			'field': field
	}

	$.ajax({
		type: 'POST',
		url: '/searcher/search/phrase',
		data: JSON.stringify(data),
		contentType: 'application/json',
		success: function(data){
			console.log(data);
			for (var i=0; i<data.length; i++){
                $('#resultTable').append('<tr>'
                +'<td>'+ data[i].title +'</td>'
                +'<td>'+ data[i].author +'</td>'
                +'<td>'+ data[i].keywords +'</td>'
                +'<td>'+ data[i].location +'</td>'
                +'<td>'+ data[i].highlight +'</td>'
                +'</tr>');
            }
		},
        error: function (e) {
        	$('#result').empty();
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSearch").prop("disabled", false);

        }
	});
}

function fuzzy(){
	var value = $('#luceneQueryLanguage input[name=query]').val();

	var field = $('#field').val();
	var result = $('#resultTable');

	var data = {
			'value': value,
			'field': field
	}

	$.ajax({
		type: 'POST',
		url: '/searcher/search/fuzzy',
		data: JSON.stringify(data),
		contentType: 'application/json',
		success: function(data){
			console.log(data);
			for (var i=0; i<data.length; i++){
                $('#resultTable').append('<tr>'
                +'<td>'+ data[i].title +'</td>'
                +'<td>'+ data[i].author +'</td>'
                +'<td>'+ data[i].keywords +'</td>'
                +'<td>'+ data[i].location +'</td>'
                +'<td>'+ data[i].highlight +'</td>'
                +'</tr>');
            }
		},
        error: function (e) {
        	$('#result').empty();
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSearch").prop("disabled", false);

        }
	});

}
});