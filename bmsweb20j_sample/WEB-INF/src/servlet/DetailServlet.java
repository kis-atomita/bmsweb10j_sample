package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import bean.Book;

import dao.BookDAO;

/**
 * 書籍管理システムにおける書籍詳細一覧機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";

		try {

			// 入力データの文字コードの指定
			request.setCharacterEncoding("UTF-8");

			// � isbnとcmd(フォワード先を区別するパラメータ)を取得
			String isbn = request.getParameter("isbn");
			cmd = request.getParameter("cmd");

			// � BookDAOをインスタンス化
			BookDAO objDao = new BookDAO();

			// � 書籍情報を検索し、戻り値としてBookオブジェクトを取得する
			Book book = objDao.selectByIsbn(isbn);

			// 詳細情報のエラーチェック
			if (book.getIsbn() == null) {
				error = "表示対象の書籍が存在しない為、詳細情報は表示出来ませんでした。";
				cmd = "list";
			}

			// � �で取得したbookをリクエストスコープに"book"という名前で格納する
			request.setAttribute("book", book);

		} catch (IllegalStateException e) {
			if (cmd.equals("detail")) {
				error = "DB接続エラーの為、書籍詳細は表示出来ませんでした。";
			} else if (cmd.equals("update")) {
				error = "DB接続エラーの為、変更画面は表示できませんでした。";
			}
			cmd = "menu";
		} finally {
			// エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// � cmdの値でフォワード先を呼び分ける
				if (cmd.equals("detail")) {
					request.getRequestDispatcher("/view/detail.jsp").forward(
							request, response);
				} else if (cmd.equals("update")) {
					request.getRequestDispatcher("/view/update.jsp").forward(
							request, response);
				}
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
