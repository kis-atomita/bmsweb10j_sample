package dao;

import java.sql.*;

import bean.Order;

/**
 * 書籍管理システムDB版で使用するデータベース関連の処理をまとめたクラス<br>
 * このクラスではデータベースから注文情報のデータ登録に関する処理がまとめられている
 *
 * @author KandaITSchool
 *
 */
public class OrderDAO {
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
	 * フィールド変数のデータベース情報を基に、DB接続をおこなう
	 *
	 * @return データベース接続情報
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
	 * DBの注文情報を格納するorderinfoテーブルへ書籍情報を登録するメソッド
	 *
	 * @param order
	 *            登録する注文情報のオブジェクト
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	public void insert(Order order) {
		Connection con = null;
		Statement smt = null;
		try {
			con = getConnection();
			smt = con.createStatement();

			String sql = "INSERT INTO orderinfo VALUES(NULL,'"
					+ order.getUserid() + "','" + order.getIsbn() + "','"
					+ order.getQuantity() + "',CURDATE())";
			smt.executeUpdate(sql);

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
	}

}
