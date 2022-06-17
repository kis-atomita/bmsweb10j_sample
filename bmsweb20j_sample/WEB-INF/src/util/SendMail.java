package util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

/**
 * メールの文字コード、送信先、件名、本文を設定し、設定した内容を送信するクラス
 *
 * @author KandaITSchool
 *
 */
public class SendMail {

	private static final String SMTP_HOST = "mail.smtp.host";
	private static final String IP_ADDRESS = "192.168.1.223";
	private static final String DEFAULT_CODE_TYPE = "iso-2022-jp";

	private final Properties props = System.getProperties();
	private final Session session = Session.getDefaultInstance(props, null);
	private final MimeMessage mimeMessage = new MimeMessage(session);

	private String codeType;
	private String text = "";

	/**
	 * コンストラクタ<br>
	 * ホスト名、IPアドレス、標準文字コードを設定
	 */
	public SendMail() {
		props.put(SMTP_HOST, IP_ADDRESS);
		this.codeType = DEFAULT_CODE_TYPE;
	}

	/**
	 * 文字コードを指定
	 *
	 * @param codeType 文字コード
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 送信元メールアドレスと送信者名を指定
	 *
	 * @param address 送信元メールアドレス
	 * @param sender 送信者名
	 */
	public void setFromInfo(String address, String sender) {

		try {
			mimeMessage.setFrom(new InternetAddress(address, sender,
					this.codeType));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 送信先メールアドレスを指定
	 *
	 * @param address 送信先アドレス
	 */
	public void setRecipients(String address) {

		try {
			mimeMessage.setRecipients(Message.RecipientType.TO, address);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * メールの件名を指定
	 *
	 * @param subject メール件名
	 */
	public void setSubject(String subject) {

		try {
			mimeMessage.setSubject(subject, this.codeType);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * メール本文を引数から書き出す。<br>
	 * このメソッドは2回目以降、前回入力された本文の後ろに改行されて付け加えられる。
	 *
	 * @param text メール本文
	 */
	public void setText(String text) {
		this.text += "\n" + text;

		try {
			mimeMessage.setText(this.text, this.codeType);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * メールの送信をおこなう<br>
	 * 送信が完了したらコンソール画面に送信成功のメッセージが出力され、
	 * 送信に失敗した場合は送信失敗のメッセージが出力される
	 */
	public void forwardMail() {

		try {

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset="
					+ this.codeType);

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

			// 送信成功
			System.out.println("送信に成功しました。");
		} catch (MessagingException e) {
			// 送信失敗
			e.printStackTrace();
			System.out.println("送信に失敗しました。");
		}

	}
}
