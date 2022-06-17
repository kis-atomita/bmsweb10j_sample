package dao;

import java.sql.*;
import bean.User;

/**
 * 書籍管理システムDB版で使用するデータベース関連の処理をまとめたクラス<br>
 * このクラスではデータベースからユーザー情報のデータ取得に関する処理がまとめられている
 *
 * @author KandaITSchool
 *
 */
public class UserDAO {
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
	 * DBのユーザー情報を格納するuserinfoテーブから、引数として与えられたユーザーIDとパスワードと一致するユーザー情報を取得する
	 *
	 * @param userid
	 *            ユーザーID
	 * @param password
	 *            パスワード
	 * @return ユーザー情報のオブジェクト<br>
	 *         ユーザー情報が見つからなかった場合は、オブジェクト内部の値が初期値の状態で返される
	 * @throws IllegalStateException
	 *             メソッド内部で例外が発生した場合
	 */
	public User selectByUser(String userid, String password) {
		Connection con = null;
		Statement smt = null;
		User user = new User();

		try {
			con = getConnection();
			smt = con.createStatement();

			String sql = "SELECT * FROM userinfo WHERE user='" + userid
					+ "' AND password='" + password + "'";

			ResultSet rs = smt.executeQuery(sql);

			if (rs.next()) {
				user.setUserid(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAuthority(rs.getString("authority"));
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

		return user;
	}
}
