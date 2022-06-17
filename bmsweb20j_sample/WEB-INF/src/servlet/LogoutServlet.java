package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 書籍管理システムにおけるログアウト機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// � セッション情報をクリアする
		HttpSession session = request.getSession();

		if (session != null) {
			session.invalidate();
		}

		// � login.jspにフォワードする
		request.getRequestDispatcher("/view/login.jsp").forward(request,
				response);
	}
}
