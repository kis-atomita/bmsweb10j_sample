<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.Book,util.MyFormat"%>

<%
	ArrayList<Book> book_list = (ArrayList<Book>) request
			.getAttribute("book_list");
	MyFormat format = new MyFormat();
%>

<html>
	<head>
		<title>購入品確認</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>
	<body>
		<!-- ブラウザ全体 -->
		<div id="wrap">

			<!-- ヘッダー部分 -->
			<%@ include file="/common/header.jsp" %>

			<!-- メニュー部分 -->
			<div id="menu">
				<div class="container">
					<!-- ナビゲーション  -->
					<div id="nav">
						<ul>
							<li><a href ="<%=request.getContextPath()%>/view/menu.jsp" >[メニュー]</a></li>
						</ul>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>購入品確認</h2>
					</div>
				</div>
			</div>

			<!-- 購入品確認コンテンツ部分 -->
			<div id="main" class="container">

				<strong class="text-left">
					下記の商品を購入しました。<br>
					ご利用ありがとうございました。
				</strong>

				<table class="list-table">
					<tr>
						<th>ISBN</th>
						<th>Title</th>
						<th>価格</th>
					</tr>

					<%
					int total = 0;
					if (book_list != null) {
						for (Book book : book_list) {
					%>
							<tr>
								<td><%=book.getIsbn() %></td>
								<td><%=book.getTitle() %></td>
								<td><%=format.moneyFormat(book.getPrice()) %></td>
							</tr>
					<%
					//合計の計算
							total += book.getPrice();
						}
					}
					%>
				</table>
				<hr>

				<table class="show-cart-sum" >
					<tr>
						<th>合計</th>
						<td><%=format.moneyFormat(total) %></td>
					</tr>
				</table>

			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>

		</div>
	</body>
</html>