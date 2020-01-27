package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class EncryptUtil {
    public static String getPasswordEncrypt(String plain_b, String salt) {
        String ret="";

        if(plain_b != null && !plain_b.equals("")) {
            byte[] bytes;
            // パスワードの保存はユーザが入力した文字列plain_b とpropertiesで用意したsaltを足して
            String password = plain_b + salt;
            try {
                // MessageDigest md = MessageDigest.getInstance("アルゴリズム名"); 　
                // →アルゴリズムに基づいたハッシュ値を求めるということ
                // アルゴリズム名はSHA-256のほかにもある　詳しくは　https://kaworu.jpn.org/java/Java%E3%81%A7SHA256%E3%82%92%E8%A8%88%E7%AE%97%E3%81%99%E3%82%8B
                // SHA-256 とは「SHA」は「Secure Hash Algorithm」の省略　
                // 大きく分けて「SHA-1」と「SHA-2」がある。
                // 「SHA-1」は何か入れると長さが160ビットの適当な値を返してくれるということ
                // 「SHA-2」はSHA-1のパワーアップバージョン
                // SHA-256は長さが２５６ビットの適当な値を返してくれるということ
                // 参考URL　https://wa3.i-3-i.info/word15997.html

                // .digest（変換したい値.getBytes()
                // digestは指定したアルゴリズム名のこと　ここではSHA-256のこと
                // ここでは変数passwordに格納された値をハッシュ値にするということ　https://weblabo.oscasierra.net/java-sha-1/
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                // byte配列から１６進数文字列に変換する　https://weblabo.oscasierra.net/java-hex-convert-1/
                ret = DatatypeConverter.printHexBinary(bytes);
            } catch(NoSuchAlgorithmException ex) {
            }
        }
        return ret;
    }
}
