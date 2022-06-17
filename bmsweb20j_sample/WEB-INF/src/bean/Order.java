package bean;

/**
 * 注文情報（オーダーNo、ユーザーID、書籍のISBN、購入する書籍数、購入日付）を 一つのオブジェクトとしてまとめるためのDTOクラス
 *
 * @author KandaITSchool
 *
 */
public class Order {

	/**
	 * 注文情報のオーダーNo
	 */
	private int orderno;
	/**
	 * 購入するユーザーのID
	 */
	private String userid;
	/**
	 * 書籍情報のISBN
	 */
	private String isbn;
	/**
	 * 購入する書籍数
	 */
	private int quantity;
	/**
	 * 購入日
	 */
	private String date;

	/**
	 * コンストラクタ<br>
	 * 注文情報（オーダーNo、ユーザーID、書籍のISBN、購入する書籍数、購入日付）の初期設定をおこなう
	 */
	public Order() {
		this.orderno = 0;
		this.userid = null;
		this.isbn = null;
		this.quantity = 0;
		this.date = null;
	}

	/**
	 * 購入情報のオーダーNoを取得する
	 *
	 * @return 購入情報のオーダーNo
	 */
	public int getOrderno() {
		return orderno;
	}

	/**
	 * 購入情報のオーダーNoを設定する
	 *
	 * @param orderno
	 *            設定する書籍情報のオーダーNo
	 */
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	/**
	 * 購入するユーザーのIDを取得する
	 *
	 * @return 購入するユーザーのID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 購入するユーザーのIDを設定する
	 *
	 * @param userid
	 *            設定するユーザーID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 購入する書籍のISBNを取得する
	 *
	 * @return 購入する書籍のISBN
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * 購入する書籍のISBNを設定する
	 *
	 * @param isbn
	 *            設定する書籍のISBN
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * 購入する書籍数を取得する
	 *
	 * @return 購入する書籍数
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 購入する書籍数を設定する
	 *
	 * @param quantity
	 *            設定する書籍数
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 購入日付を取得する
	 *
	 * @return 購入日付
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 購入日付を設定する
	 *
	 * @param date
	 *            設定する購入日付
	 */
	public void setDate(String date) {
		this.date = date;
	}
}
