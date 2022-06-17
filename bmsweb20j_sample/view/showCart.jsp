<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Order,bean.Book,java.util.*,util.MyFormat"%>

<%
	ArrayList<Book> book_list = (ArrayList<Book>) request
			.getAttribute("book_list");
	MyFormat format = new MyFormat();
%>

<html>
	<head>
		<title>カート内容</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
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
						<h2>カート内容</h2>
					</div>
				</div>
			</div>

			<!-- 書籍一覧のコンテンツ部分 -->
			<div id="main" class="container">

				<table class="list-table">
					<tr>
						<th>ISBN</th>
						<th>Title</th>
						<th>価格</th>
						<th></th>
					</tr>
					<%
					int total = 0;
					if(book_list!=null){
						for(int i=0; i<book_list.size(); i++){
					%>
							<tr>
								<td><a href="<%=request.getContextPath()%>/detail?isbn=<%=book_list.get(i).getIsbn()%>&cmd=detail "><%=book_list.get(i).getIsbn() %></a></td>
								<td><%=book_list.get(i).getTitle() %></td>
								<td><%=format.moneyFormat(book_list.get(i).getPrice()) %></td>
								<td><a href="<%=request.getContextPath()%>/showCart?delno=<%=i%>">削除</a></td>
							</tr>
					<%
							//合計の計算
							total += book_list.get(i).getPrice();
						}
					}
					%>
				</table>

				<hr/>
				<table class="total-price-table" >
					<tr>
						<th>合計</th>
						<td><%=format.moneyFormat(total) %></td>
					</tr>
				</table>

				<form action="<%=request.getContextPath()%>/buyConfirm" class="buy-button">
					<input type="submit" value=" 購入 ">
				</form>

			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>
		</div>
	</body>
</html>