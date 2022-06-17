package servlet;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import bean.Book;

import dao.BookDAO;

/**
 * 書籍管理システムにおける書籍一覧機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";

		try {
			// � BookDAOをインスタンス化する
			BookDAO objDao = new BookDAO();

			// � 関連メソッドを呼び出し、戻り値としてBookオブジェクトのリストを取得する
			ArrayList<Book> bookList = objDao.selectAll();

			// � �で取得したListをリクエストスコープに"book_list"という名前で格納する
			request.setAttribute("book_list", bookList);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
			cmd = "menu";
		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はlist.jspにフォワード
				request.getRequestDispatcher("/view/list.jsp").forward(request,
						response);
			} else {
				// エラーが有る場合はerror.jspにフォワードする
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(
						request, response);
			}
		}

	}
}
