<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "selectedPage" value = "home" scope="request" />
<!-- start of home.jsp selectedPage=${selectedPage}-->
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <H1>Home</H1>
        <c:forEach var="family" items="${familiesList}">
        <form action="./home" method="get">
            <input type="hidden" name="family" value="${family}">
            <button class="btn" type="submit" >${family}</button>
        </form>
    </c:forEach>

    <H3>Flowers</H3>
    <table class="table">
        <thead>
            <tr>
                <th scope="col">symbol</th>
                <th scope="col">synonym Symbol</th>
                <th scope="col">scientific Name with Author</th>
                <th scope="col">common Name</th>
                <th scope="col">family</th>
                <th scope="col">data Url</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="flower" items="${flowerList}">
                <tr>
                    <td>${flower.symbol}</td>
                    <td>${flower.synonymSymbol}</td>
                    <td>${flower.scientificNamewithAuthor}</td>
                    <td>${flower.commonName}</td>
                    <td>${flower.family}</td>
                    <td>${flower.dataUrl}</td>
                </tr>
            </c:forEach>

        </tbody>
</main>

<jsp:include page="footer.jsp" />
