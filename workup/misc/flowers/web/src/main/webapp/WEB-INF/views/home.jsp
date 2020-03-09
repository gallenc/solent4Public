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
    <div class="table-responsive col-md-2">
        <H3>Flowers Families</H3>
            <c:forEach var="family" items="${familiesList}">

            <form action="./home" method="get">
                <input type="hidden" name="family" value="${family}">
                <button class="btn" type="submit" >${family}</button>
            </form>
        </c:forEach>
    </div>
    <div class="table-responsive col-md-8">
        <H3>Search Criteria</H3>
        <form action="./home" method="get">
            <table class="table">
                <tbody>
                    <tr>
                        <td>Symbol</td>
                        <td><input type="text" name="symbol" value="${symbol}"></td>
                    </tr>
                    <tr>
                        <td>Synonym Symbol</td>
                        <td><input type="text" name="synonymSymbol" value="${synonymSymbol}"></td>
                    </tr>
                    <tr>
                        <td>Scientific Name with Author</td>
                        <td><input type="text" name="scientificNamewithAuthor" value="${scientificNamewithAuthor}"></td>
                    </tr>
                    <tr>
                        <td>Common Name</td>
                        <td> <input type="text" name="commonName" value="${commonName}"></td>
                    </tr>
                    <tr>
                        <td>Family</td>
                        <td><input type="text" name="family" value="${family}"></td>
                    </tr>
                </tbody>
            </table>
            <button class="btn" type="submit" >Search</button>
        </form>

        <H3>Flowers Search Results</H3>
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
                        <td><a href="${flower.dataUrl}" target="_blank" >${flower.dataUrl}</a></td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </div>
</div>

</main>

<jsp:include page="footer.jsp" />
