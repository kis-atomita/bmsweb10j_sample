<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.Book,util.MyFormat"%>

<%
	ArrayList<Book> book_list = (ArrayList<Book>) request
			.getAttribute("book_list");
	MyFormat format = new MyFormat();
%>

<html>
	<head>
		<title>初期データ登録</title>
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
						<a href="<%=request.getContextPath()%>/view/menu.jsp" >[メニュー]</a>
					</div>

					<!-- ページタイトル -->
					<div id="page_title">
						<h2>初期データ登録</h2>
					</div>
				</div>
			</div>

			<!-- 初期データ登録コンテンツ部分 -->
			<div id="main" class="container">

				<strong>初期データとして以下のデータを登録しました。</strong>
				<table class="list-table">
					<tr>
						<th>ISBN</th>
						<th>Title</th>
						<th>価格</th>
					</tr>

					<%
					if (book_list != null) {
						for (Book book : book_list) {
					%>
							<tr>
								<td><%=book.getIsbn() %></td>
								<td><%=book.getTitle() %></td>
								<td><%=format.moneyFormat(book.getPrice()) %></td>
							</tr>
					<%
						}
					}
					%>
				</table>
			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>
		</div>
	</body>
</html>