package servlet;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.BookDAO;
import bean.Book;
import bean.Order;
import bean.User;

/**
 * 書籍管理システムにおけるカート一覧機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class ShowCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {

			// � セッションから"user"を取得する
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			// セッション切れの場合はerror.jspに遷移する
			if (user == null) {
				error = "セッション切れの為、カート状況は確認出来ません。";
				cmd = "logout";
				return;
			}

			// � delnoの入力パラメータを取得する
			String delno = (String) request.getParameter("delno");

			// � セッションから"order_list"を取得する
			ArrayList<Order> order_list = (ArrayList<Order>) session
					.getAttribute("order_list");

			// � delnoが「null」でない場合、order_listから該当書籍を削除する
			if (delno != null) {
				order_list.remove(Integer.parseInt(delno));
				session.setAttribute("order_list", order_list);
			}

			// � BookDAOをインスタンス化し、関連メソッドを該当のorder_list（カートデータ）分だけ呼び出す
			BookDAO bookDaoObj = new BookDAO();

			ArrayList<Book> book_list = new ArrayList<Book>();

			if (order_list != null) {
				for (Order order : order_list) {
					Book book = bookDaoObj.selectByIsbn(order.getIsbn());
					book_list.add(book);
				}
			}

			// � 取得した各BookをListに追加し、リクエストスコープに"book_list"という名前で格納する
			request.setAttribute("book_list", book_list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、カート状況は確認出来ません。";
			cmd = "logout";

		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はshowCart.jspにフォワード
				request.getRequestDispatcher("/view/showCart.jsp").forward(
						request, response);
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
