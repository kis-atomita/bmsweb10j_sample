package bean;

/**
 * ユーザー情報（ユーザーID、パスワード、Eメールアドレス、権限）を一つのオブジェクトとしてまとめるためのDTOクラス
 *
 * @author KandaITSchool
 *
 */
public class User {

	/**
	 * ユーザーID
	 */
	private String userid;
	/**
	 * パスワード
	 */
	private String password;
	/**
	 * Eメールアドレス
	 */
	private String email;
	/**
	 * ユーザー情報の権限<br>
	 * 1の場合は「一般ユーザー」、2の場合は「管理者」に該当する
	 */
	private String authority;

	/**
	 * コンストラクタ<br>
	 * ユーザー情報（ユーザーID、パスワード、Eメールアドレス、権限）の初期設定をおこなう
	 */
	public User() {
		userid = null;
		password = null;
		email = null;
		authority = null;
	}

	/**
	 * ユーザー情報のユーザーIDを取得する
	 *
	 * @return ユーザー情報のユーザーID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * ユーザー情報のユーザーIDを設定する
	 *
	 * @param userid
	 *            設定するユーザーID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * ユーザー情報のパスワードを取得する
	 *
	 * @return ユーザー情報のパスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * ユーザー情報のパスワードを設定する
	 *
	 * @param password
	 *            設定するパスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ユーザー情報のEメールアドレスを取得する
	 *
	 * @return ユーザー情報のEメールアドレス
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ユーザー情報のEメールアドレスを設定する
	 *
	 * @param email
	 *            設定するEメールアドレス
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * ユーザー情報の権限を取得する
	 *
	 * @return ユーザー情報の権限<br>
	 *         1の場合は「一般ユーザー」、2の場合は「管理者」に該当する
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * ユーザー情報の権限を取得する
	 *
	 * @param authority
	 *            ユーザー情報の権限<br>
	 *            1の場合は「一般ユーザー」、2の場合は「管理者」に該当する
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
