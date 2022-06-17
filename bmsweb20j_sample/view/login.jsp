<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>

<%
	//各データをセッションから取得
	String userid = "";
	String password = "";
	String error = (String) request.getAttribute("error");
	Cookie[] cookieList = request.getCookies(); //クッキーの取得

	//クッキーがあるか判定
	if (cookieList != null) {
		for (Cookie cookie : cookieList) {
			//クッキーからユーザー情報の取得
			if (cookie.getName().equals("user")) {
				userid = cookie.getValue();
			}
			//クッキーからパスワード情報の取得
			if (cookie.getName().equals("password")) {
				password = cookie.getValue();
			}
		}
	}
	if (error == null) {
		error = "";
	}
%>
<html>
	<head>
		<title>ログイン画面</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
	</head>

	<body>
		<!-- ブラウザ全体 -->
		<div id="wrap">
			<!-- ヘッダー部分 -->
			<%@ include file="/common/header.jsp" %>

			<!-- ログインのコンテンツ部分 -->
			<div id="main" class="container">

				<p><%=error %></p>
				<form action="<%=request.getContextPath()%>/login" method="POST">
					<table class="input-table">
						<tr>
							<th>ユーザー</th>
							<td>
								<input type="text" size="25" name="id" value="<%=userid %>">
							</td>
						</tr>
						<tr>
							<th>パスワード</th>
							<td>
								<input type="password" size="25" name="password" value="<%=password %>">
							</td>
						</tr>
					</table>
					<input type="submit" value="ログイン">
				</form>
			</div>

			<!-- フッター部分 -->
			<%@ include file="/common/footer.jsp" %>
		</div>
	</body>
</html>