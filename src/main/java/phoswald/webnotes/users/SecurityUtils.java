package phoswald.webnotes.users;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class SecurityUtils {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    private static final String HASH_ALGORITHM = "SHA-1";

    public static String computeHash(String secret) {
        try {
            Objects.requireNonNull(secret);
            String salt = Long.toString(System.nanoTime() ^ System.currentTimeMillis());
            String digest = encode(hash(salt, secret));
            return salt + ";" +digest;
        } catch(NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static boolean verifyHash(String hash, String secret) {
        try {
            Objects.requireNonNull(hash);
            Objects.requireNonNull(secret);
            int sep = hash.lastIndexOf(';');
            String salt = hash.substring(0, sep);
            String digest = encode(hash(salt, secret));
            return hash.equals(salt + ";" + digest);
        } catch(NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static byte[] hash(String salt, String secret) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(HASH_ALGORITHM).digest((salt + ";" + secret).getBytes(UTF_8));
    }

    private static String encode(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for(byte b : buf) {
            sb.append(HEX_CHARS[(b & 0xF0) >>> 4]);
            sb.append(HEX_CHARS[ b & 0x0F]);
        }
        return sb.toString();
    }
}
