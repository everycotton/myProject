<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>SIST Movie Center</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
<link rel="stylesheet" href="movie/css/style.css">
</head>

<body>
	<section id="movies">

		<h2>List of Movies</h2>

	</section>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script>
		$(function(){
			var data = {
					 movies: ${json}
			};

					for (var i = 0; i < data.movies.length; i++) {
					  
					  // create a new <div> to hold our movie list
					  $("<div />", {
					    // apply class
					    class: "module module-movie",
					    // business logic
					    click: function(){
					      alert("added to movie list");
					    },
					    // apply CSS
					    css: { 
					      "border": "1px solid black",
					      "background-image": "url("+data.movies[i].movieImage+")"
					    },
					    // the HTML template
					    html: 
					    
					      "<div class='movie-info'>" +
					        "<h3 class='movie-title'>" +
					          data.movies[i].movieTitle +
					        "</h3>" +
					        "<p>" +
					          data.movies[i].movieDirector +
					        "</p>" +
					      "</div>"
					    
					  }).appendTo("#movies");  // append to #movies section
					}
		});
		
	</script>
</body>
</html>
