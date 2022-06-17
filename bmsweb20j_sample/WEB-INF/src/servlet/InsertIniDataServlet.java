package servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import util.FileIn;
import java.util.ArrayList;
import bean.Book;
import dao.BookDAO;

/**
 * 書籍管理システムにおける初期データ登録機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class InsertIniDataServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";
		FileIn in = new FileIn();

		try {
			// � BookDAOをインスタンス化し、書籍情報の全件取得をおこなう
			BookDAO bookDaoObj = new BookDAO();

			// � �の戻り値として、Bookオブジェクトのリストを取得する
			ArrayList<Book> list = bookDaoObj.selectAll();

			// � �のListに1件でも書籍データがあればerror.jspにフォワードする
			if (!list.isEmpty()) {
				error = "DBにはデータが存在するので、初期データ登録は出来ません。";
				cmd = "menu";
				return;
			}

			// � 初期データ用CSVファイルよりデータを取得する
			String path = getServletContext().getRealPath("file\\initial_data.csv");

			// ファイルのオープンをおこない、ファイルのオープンに失敗した場合はerror.jspにフォワードする
			if (!(in.open(path))) {
				error = "初期データファイルが無い為、登録は行えません。";
				cmd = "menu";
				return;
			}

			// � Bookオブジェクトを生成し、setterを利用して�データのisbn,title,priceを設定する
			String str = null;
			while ((str = in.readLine()) != null) {

				String[] data = str.split(",");

				Book book = new Book();
				book.setIsbn(data[0]);
				book.setTitle(data[1]);
				book.setPrice(Integer.parseInt(data[2]));

				// 取得した各BookをListに追加
				list.add(book);
			}

			// � 取得した各BookをListに追加し、リクエストスコープに"book_list"という名前で格納する
			request.setAttribute("book_list", list);

			// � �で設定した各Bookのオブジェクトを引数として書籍をbookinfoに登録する
			for (Book book : list) {
				bookDaoObj.insert(book);
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、初期データ登録は行えません。";
			cmd = "logout";

		} catch (NumberFormatException e) {
			error = "初期データに不備がある為、登録は行えません。";
			cmd = "menu";

		} finally {
			// ファイルのクローズ
			in.close();

			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合、insertIniData.jspにフォワード
				request.getRequestDispatcher("/view/insertIniData.jsp")
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
