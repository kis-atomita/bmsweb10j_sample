package bean;

/**
 * 購入履歴情報（購入したユーザーのID、購入した書籍のタイトル、購入日付）を一つのオブジェクトとしてまとめるためのDTOクラス
 *
 * @author instructor01
 *
 */
public class OrderedItem {

	/**
	 * 購入したユーザーのID
	 */
	private String userid;
	/**
	 * 購入した書籍のタイトル
	 */
	private String title;
	/**
	 * 購入日付
	 */
	private String date;

	/**
	 * コンストラクタ<br>
	 * 購入履歴情報（購入したユーザーのID、購入した書籍のタイトル、購入日付）の初期設定をおこなう
	 */
	public OrderedItem() {
		this.userid = null;
		this.title = null;
		this.date = null;
	}

	/**
	 * 購入したユーザーのIDを取得する
	 *
	 * @return 購入したユーザーのID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 購入したユーザーのIDを設定する
	 *
	 * @param userid
	 *            設定するユーザーID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 購入した書籍のタイトルを取得する
	 *
	 * @return 購入した書籍のタイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 購入した書籍のタイトルを設定する
	 *
	 * @param title
	 *            設定する書籍タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 購入した書籍の日付を取得する
	 *
	 * @return 購入日付
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 購入した書籍の日付を設定する
	 *
	 * @param date
	 *            設定する購入日付
	 */
	public void setDate(String date) {
		this.date = date;
	}
}
