<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Book"%>

<%
	Book book = (Book) request.getAttribute("book");
%>

<html>
	<head>
		<title>カート追加</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>

	<body>
		<!-- ブラウザ全体 -->
		<div id="wrap">

			<!--ヘッダー部分  -->
			<%@ include file="/common/header.jsp" %>

			<!-- メニュー部分 -->
			<div id="menu">
				<div class="container">
					<!-- ナビゲーション  -->
					<div id="nav">
						<ul>
							<li><a href ="<%=request.getContextPath()%>/view/menu.jsp" >[メニュー]</a></li>
							<li><a href ="<%=request.getContextPath()%>/list">[書籍一覧]</a></li>
						</ul>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>カート登録</h2>
					</div>
				</div>
			</div>

			<!-- 書籍一覧のコンテンツ部分 -->
			<div id="main" class="container">

				<strong>下記の書籍をカートに追加しました。</strong>

				<table class="detail-table">
					<tr>
						<th>ISBN</th>
						<td><%=book.getIsbn() %></td>
					</tr>
					<tr>
						<th>TITLE</th>
						<td><%=book.getTitle() %></td>
					</tr>
					<tr>
						<th>価格</th>
						<td><%=book.getPrice() %></td>
					</tr>
				</table>

				<form action="<%=request.getContextPath() %>/showCart">
					<input type="submit" value="カート確認">
				</form>


			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>
		</div>
	</body>
</html>