package servlet;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import util.SendMail;
import dao.BookDAO;
import dao.OrderDAO;
import bean.Book;
import bean.Order;
import bean.User;

/**
 * 書籍管理システムにおける書籍購入機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class BuyConfirmServlet extends HttpServlet {

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
				error = "セッション切れの為、購入は出来ません。";
				cmd = "login";
				return;
			}

			// � セッションからorder_listを取得する
			ArrayList<Order> order_list = (ArrayList<Order>) session.getAttribute("order_list");

			// カートの中身がない場合はerror.jspに遷移する
			if (order_list == null || order_list.isEmpty()) {
				error = "カートの中に何も無かったので購入は出来ません。";
				cmd = "menu";
				return;
			}

			// �各DAOをインスタンス化し、関連メソッドをorder_listのカート追加データ分だけ呼び出す
			BookDAO bookDaoObj = new BookDAO();
			OrderDAO orderDaoObj = new OrderDAO();

			ArrayList<Book> book_list = new ArrayList<Book>();

			for (Order order : order_list) {
				Book book = bookDaoObj.selectByIsbn(order.getIsbn());
				book_list.add(book);
				orderDaoObj.insert(order);
			}

			// � �中に取得した各BookをListに追加し、リクエストスコープに"book_list"という名前で格納する
			request.setAttribute("book_list", book_list);

			// � "order_list"の注文情報内容をメール送信する。
			SendMail sendMail = new SendMail();
			sendMail.setFromInfo("info@kanda-it-school.com", "書籍管理システム");
			sendMail.setRecipients(user.getEmail());
			sendMail.setSubject("本のご購入ありがとうございます。");
			sendMail.setText(user.getUserid()
					+ "様\n\n本のご購入ありがとうございます。\n以下内容でご注文を受け付けましたので、ご連絡致します。\n");

			int sum = 0;
			for (Book book : book_list) {
				sendMail.setText(book.getIsbn() + " " + book.getTitle() + " "
						+ book.getPrice() + "円");
				sum += book.getPrice();
			}

			sendMail.setText("合計 " + sum + "円\n\nまたのご利用よろしくお願いします。");
			sendMail.forwardMail();

			// � セッションのorder_listをクリアする
			session.setAttribute("order_list", null);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入は出来ません。";
			cmd = "logout";

		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はbuyConfirm.jspにフォワードする
				request.getRequestDispatcher("/view/buyConfirm.jsp").forward(
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
