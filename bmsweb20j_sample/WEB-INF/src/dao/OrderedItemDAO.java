package dao;

import java.sql.*;
import java.util.ArrayList;

import bean.OrderedItem;

/**
 * 書籍管理システムDB版で使用するデータベース関連の処理をまとめたクラス<br>
 * このクラスではデータベースから購入情報のデータ取得に関する処理がまとめられている
 *
 * @author KandaITSchool
 *
 */
public class OrderedItemDAO {
	/**
	 * JDBCドライバ内部のDriverクラスパス
	 */
	private static final String RDB_DRIVE = "com.mysql.jdbc.Driver";
	/**
	 * 接続するMySQLデータベースパス
	 */
	private static final String URL = "jdbc:mysql://localhost/mybookdb";
	/**
	 * データベースのユーザー名
	 */
	private static final String USER = "root";
	/**
	 * データベースのパスワード
	 */
	private static final String PASSWD = "root123";

	/**
	 * フィールド変数の情報を基に、DB接続をおこなうメソッド
	 *
	 * @return データベース接続情報]
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	private static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(RDB_DRIVE);
			con = DriverManager.getConnection(URL, USER, PASSWD);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * DBのbookinfoとorderinfoのテーブルから購入履歴情報を全件取得するメソッド
	 *
	 * @return : 購入履歴情報の全リスト
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	public ArrayList<OrderedItem> selectAll() {
		Connection con = null;
		Statement smt = null;
		ArrayList<OrderedItem> ordered_list = new ArrayList<OrderedItem>();

		try {
			con = getConnection();
			smt = con.createStatement();

			String sql = "SELECT o.user,b.title,o.date from bookinfo b inner join orderinfo o on b.isbn=o.isbn";
			ResultSet rs = smt.executeQuery(sql);

			while (rs.next()) {
				OrderedItem orderedItem = new OrderedItem();
				orderedItem.setUserid(rs.getString("user"));
				orderedItem.setTitle(rs.getString("title"));
				orderedItem.setDate(rs.getString("date"));

				ordered_list.add(orderedItem);
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return ordered_list;
	}
}
