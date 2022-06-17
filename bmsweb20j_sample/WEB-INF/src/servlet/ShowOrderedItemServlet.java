package servlet;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import bean.OrderedItem;
import dao.OrderedItemDAO;

/**
 * 書籍管理システムにおける購入履歴一覧機能に関する処理をおこなうサーブレットクラス
 *
 * @author KandaITSchool
 *
 */
public class ShowOrderedItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String error = "";
		String cmd = "";

		try {

			// � OrderedItemDAOをインスタンス化し、関連メソッドを呼び出す
			OrderedItemDAO orderedItemDao = new OrderedItemDAO();

			// � 戻り値として、OrderItemオブジェクトのリストを取得する
			ArrayList<OrderedItem> ordered_list = orderedItemDao.selectAll();

			// � 取得したListをリクエストスコープに
			request.setAttribute("ordered_list", ordered_list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入状況確認は出来ません。";
			cmd = "logout";
		} finally {
			// � エラーの有無でフォワード先を呼び分ける
			if (error.equals("")) {
				// エラーが無い場合はshowOrderedItem.jspにフォワードする
				request.getRequestDispatcher("/view/showOrderedItem.jsp")
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
