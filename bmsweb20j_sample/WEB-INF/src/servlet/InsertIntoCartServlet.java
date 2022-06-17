package servlet;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import bean.Book;
import bean.Order;
import bean.User;
import dao.BookDAO;

/**
 * 書籍管理システムにおけるカート登録機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class InsertIntoCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = "";
		String cmd = "";

		try {
			// � セッションからuserオブジェクトを取得
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			// セッション切れの場合はエラー
			if (user == null) {
				error = "セッション切れの為、カートに追加できません";
				cmd = "logout";
				return;
			}

			// � isbnのパラメータを取得
			String isbn = (String) request.getParameter("isbn");

			// � BookDAOをインスタンス化し、書籍情報の検索をおこなう
			BookDAO bookDaoObj = new BookDAO();
			Book book = bookDaoObj.selectByIsbn(isbn);

			// � リクエストスコープに"book"という名前で格納する
			request.setAttribute("book", book);

			// � Orderのインスタンスを生成し、各setterメソッドを利用し、isbn,userid,数量（1固定）を設定する
			Order order = new Order();
			order.setUserid(user.getUserid());
			order.setIsbn(book.getIsbn());
			order.setQuantity(1);

			// � セッションからorder_listのList配列を取得する
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

			// 取得できなかった場合はArrayList<Order>配列を新規で作成する
			if (order_list == null) {
				order_list = new ArrayList<Order>();
			}

			// � OrderオブジェクトをList配列に追加し、セッションスコープに"order_list"という名前で登録する
			order_list.add(order);
			session.setAttribute("order_list", order_list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、カートに追加は出来ません。";
			cmd = "logout";
		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はinsertIntoCart.jspにフォワード
				request.getRequestDispatcher("/view/insertIntoCart.jsp")
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
