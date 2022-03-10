package bms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 書籍管理システムDB版で使用するデータベース関連の処理をまとめたクラス
 *
 * @author KandaITSchool
 *
 */
public class BookDAO {

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
	 * @return データベース接続情報
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
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
	 * DBの書籍情報を格納するbookinfoテーブルから全書籍情報を取得するメソッド
	 *
	 * @return 全書籍情報のリスト
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
	 */
	public ArrayList<Book> selectAll() {
		Connection con = null;
		Statement smt = null;
		ArrayList<Book> bookList = new ArrayList<Book>();

		try {
			con = getConnection();
			smt = con.createStatement();

			String sql = "SELECT * FROM bookinfo ORDER BY isbn";
			ResultSet rs = smt.executeQuery(sql);

			while (rs.next()) {
				Book book = new Book();
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
				bookList.add(book);
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
		return bookList;
	}

	/**
	 * 引数で与えられた書籍情報を、書籍データを格納するbookinfoテーブルへ登録するメソッド
	 *
	 * @param _book 登録する書籍情報の{@link Book}オブジェクト
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
	 */
	public void insert(Book _book) {
		Connection con = null;
		Statement smt = null;
		try {
			con = getConnection();
			smt = con.createStatement();
			String sql = "INSERT INTO bookinfo(isbn,title,price) VALUES('" + _book.getIsbn() + "','" + _book.getTitle()
					+ "'," + _book.getPrice() + ")";
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

	/**
	 * 書籍情報を格納するbookinfoテーブルから、引数で与えられたISBNを持つ書籍データの削除をおこなうメソッド
	 *
	 * @param _isbn 削除対象のISBN
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
	 */
	public void delete(String _isbn) {
		Connection con = null;
		Statement smt = null;
		try {
			con = getConnection();
			smt = con.createStatement();
			String sql = "DELETE FROM bookinfo WHERE isbn='" + _isbn + "'";
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

	/**
	 * 書籍情報を格納するbookinfoテーブルに存在する、引数で与えられたISBNを持つ書籍情報を、 引数で与えられた書籍情報に変更をおこなうメソッド
	 *
	 * @param _book 更新する書籍情報の{@link Book}オブジェクト
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
	 */
	public void update(Book _book) {
		Connection con = null;
		Statement smt = null;
		try {
			con = getConnection();
			smt = con.createStatement();
			String sql = "UPDATE bookinfo SET" + " title ='" + _book.getTitle() + "'," + " price =" + _book.getPrice()
					+ " WHERE isbn ='" + _book.getIsbn() + "'";
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

	/**
	 * 引数のISBNを基にDBの書籍情報を格納するbookinfoテーブルから該当書籍データの検索をおこなうメソッド
	 *
	 * @param _isbn 検索対象のISBN
	 * @return 検索結果の書籍情報の{@link Book}オブジェクト<br>
	 *         書籍情報が見つからなかった場合は、オブジェクト内部の値が初期値の状態で返される
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
	 */
	public Book selectByIsbn(String _isbn) {
		Book book = new Book();
		Connection con = null;
		Statement smt = null;
		try {
			con = getConnection();
			smt = con.createStatement();
			String sql = "SELECT * FROM bookinfo WHERE isbn='" + _isbn + "'";
			ResultSet rs = smt.executeQuery(sql);

			if (rs.next()) {
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
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
		return book;
	}

	/**
	 * 引数の各データを基にDBの書籍情報を格納するbookinfoテーブルから該当書籍データの絞込み検索処理をおこなうメソッド
	 *
	 * @param _isbn  検索対象のISBN
	 * @param _title 検索対象のTITLE
	 * @param _price 検索対象の価格
	 * @return 該当書籍データのリスト<br>
	 *         該当書籍が見つからなかった場合はリストが空の状態で返される
	 * @throws IllegalStateException メソッド内部で例外が発生した場合
	 */
	public ArrayList<Book> search(String _isbn, String _title, String _price) {
		Connection con = null;
		Statement smt = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try {
			con = getConnection();
			smt = con.createStatement();

			String sql = "SELECT * FROM bookinfo WHERE isbn LIKE '%" + _isbn + "%' AND title LIKE '%" + _title
					+ "%' AND price LIKE '%" + _price + "%'";

			ResultSet rs = smt.executeQuery(sql);

			while (rs.next()) {
				Book book = new Book();
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
				bookList.add(book);
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
		return bookList;
	}

}
