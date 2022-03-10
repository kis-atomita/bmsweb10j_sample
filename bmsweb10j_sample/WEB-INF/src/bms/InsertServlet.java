package bms;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 書籍管理システムにおける書籍登録機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class InsertServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		String cmd = "";

		try {
			// 入力データの文字コードの指定
			request.setCharacterEncoding("UTF-8");

			// ①isbn、title、price等の入力パラメータを取得する
			String isbn = request.getParameter("isbn");
			String title = request.getParameter("title");
			String strPrice = request.getParameter("price");

			// ②取得したパラメータの各エラーチェックをおこなう
			// 全データの空白チェック（データが入力されているかどうか）
			if (isbn.equals("")) {
				error = "ISBNが未入力の為、書籍登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			if (title.equals("")) {
				error = "タイトルが未入力の為、書籍登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			if (strPrice.equals("")) {
				error = "価格が未入力の為、書籍登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			int price;
			try {
				// 価格値チェック（整数かどうか）
				price = Integer.parseInt(strPrice);
			} catch (NumberFormatException e) {
				error = "価格の値が不正の為、書籍登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			// BookDAOオブジェクト生成
			BookDAO objDao = new BookDAO();

			// ISBNの重複チェック
			if (objDao.selectByIsbn(isbn).getIsbn() != null) {
				error = "入力ISBNは既に登録済みの為、書籍登録処理は行えませんでした。";
				cmd = "list";
				return;
			}

			// ③Bookのオブジェクトを生成し、各setterメソッドを利用し、isbn、title、priceを設定する
			Book book = new Book();
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setPrice(price);

			// ④③で生成したBookのオブジェクトを引数として、関連メソッドを呼び出す
			objDao.insert(book);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、書籍登録処理は行えませんでした。";
			cmd = "menu";
		} finally {
			// ⑤エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はListServletにフォワード
				request.getRequestDispatcher("/list").forward(request, response);
			} else {
				// エラーが有る場合はerror.jspにフォワードする
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}
