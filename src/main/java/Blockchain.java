import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blockchain {
    public List<Block> chain;
    public int difficulty;

    public Blockchain() {
        this.difficulty = 4;

        this.chain = new ArrayList<>();
        chain.add(createGenesis());
    }

    /**
     * create genesis block
     * @return
     */
    private Block createGenesis() {
        return new Block(0, new Date(), "genesis data");
    }

    public Block getLatestBlock() {
        return chain.getLast();
    }

    public void addBlock(Block newBlock) {
        newBlock.prevHash = getLatestBlock().curHash;
        newBlock.mineBlock(this.difficulty);
        chain.add(newBlock);
    }

    /**
     * check if the blockchain is valid by going through the chain and
     * check every single block in it. There are 2 values of the block
     * that need to be checked:
     * (1) current hash value
     * (2) previous hash value
     * @return true - when the blockchain isn't tampered with;
     *         false -otherwise
     */
    public boolean checkValid() {

        for (int i = 1; i < chain.size(); i++) {
            Block prevBlock = chain.get(i - 1);
            Block curBlock = chain.get(i);
            if (!curBlock.curHash.equals(curBlock.calculateHash())) {
                System.out.println("[!] Current hash value is invalid!");
                System.out.println(curBlock.curHash);
                System.out.println(curBlock.calculateHash());
                return false;
            }

            if (!curBlock.prevHash.equals(prevBlock.calculateHash())) {
                System.out.println("[!] Previous hash value is invalid!");
                System.out.println(curBlock.prevHash);
                System.out.println(prevBlock.calculateHash());
                return false;
            }
        }

        return true;
    }
}
