package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import dao.BookDAO;

/**
 * 書籍管理システムにおける書籍削除機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class DeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";

		try {
			// 入力データの文字コードの指定
			request.setCharacterEncoding("UTF-8");

			// � isbnのパラメータを取得する
			String isbn = (String) request.getParameter("isbn");

			// � DAOオブジェクトをインスタンス化する
			BookDAO objDao = new BookDAO();

			// 更新対象の有無のエラーチェック
			if (objDao.selectByIsbn(isbn).getIsbn() == null) {
				error = "削除対象の書籍が存在しない為、書籍削除処理は行えませんでした。";
				cmd = "list";
				return;
			}

			// � 書籍の削除をおこなうメソッドを呼び出す
			objDao.delete(isbn);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍削除処理は行えませんでした。";
			cmd = "menu";
		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はListServletにフォワード
				request.getRequestDispatcher("/list")
						.forward(request, response);
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
