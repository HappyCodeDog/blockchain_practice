import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class BlockchainTest {

    /**
     * add 2 blocks (besides genesis block) into the blockchain,
     * then verify whether the blockchain is valid, which means
     * it hasn't been tampered with
     */
    public static void main(String[] args) throws Exception {
        Blockchain blockchain = new Blockchain();

        System.out.println("Mining first block...");
        blockchain.addBlock(new Block(1, new Date(), "This is first block!"));

        System.out.println("Mining second block...");
        blockchain.addBlock(new Block(2, new Date(), "This is second block!"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(blockchain.chain);
        System.out.println(json);

        System.out.println("Verifying block!");
        System.out.println("Result of verification: " + blockchain.checkValid());

    }
}
