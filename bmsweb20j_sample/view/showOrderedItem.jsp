<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,bean.OrderedItem"%>

<%
	ArrayList<OrderedItem> ordered_list = (ArrayList<OrderedItem>) request
			.getAttribute("ordered_list");
%>

<html>
	<head>
		<title>購入状況</title>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
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
						<h2>購入状況</h2>
					</div>
				</div>
			</div>

			<!--コンテンツ見出し部分  -->
			<div id="main" class="container">
				<table class="list-table">
					<tr>
						<th>ユーザー</th>
						<th>Title</th>
						<th>注文日</th>
					</tr>

					<%
					if (ordered_list != null) {
						for (OrderedItem ordered : ordered_list) {
					%>
							<tr>
								<td><%=ordered.getUserid() %></td>
								<td><%=ordered.getTitle() %></td>
								<td><%=ordered.getDate().replace("-","/") %></td>
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