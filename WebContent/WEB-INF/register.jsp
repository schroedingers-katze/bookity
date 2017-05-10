<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>bookity | Sign up!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
  </head>
 
  <body>
     
    <jsp:include page="fragment/navbar.jspf" />
     
    <div class="container">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <form class="form-signin" method="post" action="register">
                <h2 class="form-signin-heading">Sign up</h2>
                <input name="inputEmail" type="email" class="form-control" placeholder="E-mail" required autofocus />
                <input name="inputUsername" type="text" class="form-control" placeholder="Username" required autofocus />
                <input name="inputPassword" type="password" class="form-control" placeholder="Password" required />
                <button class="btn btn-lg btn-basic btn-block" type="submit" >Sign up</button>
            </form>
        </div>
    </div>
     
    <jsp:include page="fragment/footer.jspf" />
     
    <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="resources/js/bootstrap.js"></script>
  </body>
</html>