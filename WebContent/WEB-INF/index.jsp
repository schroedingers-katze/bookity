<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <title>bookity | Homepage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
  	<link rel="icon" href="twobooks.png">
  </head>
 
  <body>     
    <jsp:include page="fragment/navbar.jspf" />
     
    <c:if test="${not empty requestScope.books}">

        <c:forEach var="book" items="${requestScope.books}">

            <div class="container">            
              <div class="row bs-callout bs-callout-default">

                  <c:if test="${book.checking != 0}">                                            
	                <div class="col col-lg-2 col-md-2 col-sm-2">
	 					<img class="bookcover" src="img?book_id=${book.id}">
	                </div>
	              </c:if>
	              
                  <c:if test="${book.checking eq 0}"> 
                  	<div class="col col-lg-2 col-md-2 col-sm-2">                                                             	              
	              		<img class="bookcover" src="cover.jpg">
	              	</div>	              		
	              </c:if>
	                           
                <div class="col col-lg-9 col-md-9 col-sm-9" id="info">
                <h6><small>Added by: <c:out value="${book.user.username}" />, 
                 <fmt:formatDate value="${book.timestamp}" pattern="dd/MM/YYYY"/></small></h6>
                  
                <h4 class="booktitle"><c:out value="${book.title}" /></h4>
                <p><h5 class="centered"><c:out value="${book.author}" /></h5></p>
                  
				<p> Publisher: <c:out value="${book.publisher}" /></p>
                <p><c:out value="${book.description}" /></p>
            	</div>
          	</div>
          </div>
        </c:forEach>
    </c:if>
         
 	<jsp:include page="fragment/footer.jspf" />
  
    <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="resources/js/bootstrap.js"></script>
  </body>
</html>