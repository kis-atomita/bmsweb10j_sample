<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
	//セッションからユーザー情報を取得
	User user = (User) session.getAttribute("user");
	//セッション切れか確認
	if (user == null) {
		//セッション切れならerror.jspへフォワード
		request.setAttribute("error", "セッション切れの為、メニュー表示できませんでした。");
		request.setAttribute("cmd", "logout");
		request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		return;
	}

	//権限の設定
	String authority = "";
	if (user.getAuthority().equals("1")) {
		authority = "一般ユーザー";
	} else if (user.getAuthority().equals("2")) {
		authority = "管理者";
	}
%>

<div id="user_info">
	<ul>
		<li>名前: <%=user.getUserid() %></li>
		<li>権限: <%=authority %></li>
	</ul>
</div>