import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;

/**
 * Block
 */
public class Block {
    public int index;
    public Date timestamp;
    public String data;
    public String prevHash;
    public String curHash;
    public int nonce;

    public Block(int index, Date timestamp, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.nonce = 0;

        this.curHash = calculateHash();
    }

    /**
     * calculate the hash value of current block using SHA-256
     * @return
     */
    public String calculateHash() {
        String input = index + timestamp.toString() + data + prevHash + nonce;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

            // Convert the byte array to a hexadecimal string
            Formatter formatter = new Formatter();
            for (byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            String result = formatter.toString();
            formatter.close();

            return result;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }

    /**
     * mine block under given difficulty
     */
    public void mineBlock(int difficulty) {
        while (!checkHash(curHash, difficulty)) {
            ++nonce;
            curHash = calculateHash();
        }

        System.out.println("Block mined: " + curHash);
    }

    /**
     * check if the hash value starts with (difficulty + 1) zeros
     * @param hash
     * @param difficulty
     * @return
     */
    private boolean checkHash(String hash, int difficulty) {
        for (int i = 0; i < difficulty; i++) {
            if ('0' != hash.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
