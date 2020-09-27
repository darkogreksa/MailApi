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

//	$('#resultTable').on('click','td#btnDownload', function(e){
//		e.preventDefault();
//
//		console.log('cliiik');
//		download(token);
//	});

//	$('#btnDownload').click(function(event){
//		console.log('cliik')
//	});
});


function regular(){

	var value = $('#luceneQueryLanguage input[name=query]').val();
	var field = $('#field').val();
	var result = $('#resultTable');


	var data = {
			'value': value,
			'field': field
	}

	$.ajax({
		type: 'POST',
		url: '/searcher/search/term',
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
                +'<td><input type="button" id="btnDownload" name="'+ file +'" onclick="download()" value="Download""/></td>'
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

function download(){
	console.log('cliik');
	 var fi = localStorage.getItem('f');
     var splited = fi.split('files\\');
     var splitedFile = splited[1].split('.');
     var file = splitedFile[0];
     var xhr = new XMLHttpRequest();
     xhr.open('GET', '/api/index/download/' + file, true);
     console.log(file);
     xhr.responseType = 'blob';


		xhr.onload = function(e) {
			if (this.status == 200) {
				var blob = this.response;
				console.log(blob);
				var a = document.createElement('a');
				var url = window.URL.createObjectURL(blob);
				a.href = url;
				a.download = xhr.getResponseHeader('filename');
				a.click();
				window.URL.revokeObjectURL(url);
			}

		};


     xhr.send();
}