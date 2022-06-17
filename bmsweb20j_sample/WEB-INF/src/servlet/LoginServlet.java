package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import bean.User;
import dao.UserDAO;

/**
 * 書籍管理システムにおけるログイン機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {

			// � userid,password入力パラメータを取得する
			String userid = (String) request.getParameter("id");
			String password = (String) request.getParameter("password");

			// � UserDAOをインスタンス化し、ユーザー情報の検索をおこなう
			UserDAO userDaoObj = new UserDAO();
			User user = userDaoObj.selectByUser(userid, password);

			// ユーザー情報のチェック
			if (user.getUserid() == null) {
				error = "入力データが間違っています。";
				cmd = "login";
				return;
			}

			// ユーザー情報がある場合、セッションにスコープにuserという名前で登録する
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			// クッキーに入力情報のuseridとpasswordを登録する（期限は5日間）
			Cookie userCookie = new Cookie("user", user.getUserid());
			userCookie.setMaxAge(60 * 60 * 24 * 5);
			response.addCookie(userCookie);

			Cookie passCookie = new Cookie("password", user.getPassword());
			passCookie.setMaxAge(60 * 60 * 24 * 5);
			response.addCookie(passCookie);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ログイン出来ません。";
			cmd = "login";

		} finally {
			if (error.equals("")) {
				request.getRequestDispatcher("/view/menu.jsp").forward(request,
						response);
			} else {
				request.setAttribute("error", error);
				if (cmd.equals("login")) {
					request.getRequestDispatcher("/view/login.jsp").forward(
							request, response);
				} else {
					request.setAttribute("cmd", cmd);
					request.getRequestDispatcher("/view/error.jsp").forward(
							request, response);
				}
			}
		}

	}
}
