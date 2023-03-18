package blockchain;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import static blockchain.Main.*;

public class Blockchain implements Serializable {

    public static String[][] blockChainInfo = {{"0", "0", "0", "0", "0", "0", "0"}};
    public transient volatile static String[][] blockChainWaitList = {{"0", "0", "0", "0", "0", "0", "0"}};


    public static String ChangeZeros(long duration) {
        if (duration > 3) {
            Main.zeros -= 1;
            return "N was decreased by 1";
        } else if (duration <= 3 || duration == 0) {
            Main.zeros +=1;
            return "N was increased by 1";
        }
        return null;
    }

    public static void VerifBlockchain() throws IOException {

        int g;
        for (g = 1; g < blockChainInfo.length;) {
            if ((Integer.valueOf(blockChainInfo[g][0]) == (Integer.valueOf(blockChainInfo[g-1][0]) + 1) &&
                Long.valueOf(blockChainInfo[g][1]) >= Long.valueOf(blockChainInfo[g-1][1]) &&
                blockChainInfo[g][3].equals(blockChainInfo[g-1][4]) &&
                Miner.GenSha256(blockChainInfo[g][2] + blockChainInfo[g][5]).equals(blockChainInfo[g][4])) && Integer.parseInt(blockChainInfo[g][6]) >= Thread.activeCount()) {

                g++;
            } else {
                blockChainInfo = EraseBlock(blockChainInfo, g);
                System.out.println("Blockchain corrected at " + g);
                }

            }
        Storage.Serialize(Blockchain.blockChainInfo,storagePath);
    }

    public static String[][] EraseBlock(String[][] blockChain, int blockToErase) {
        while (blockToErase != blockChain.length) {
            blockChain[blockToErase] = null;
            blockChain = Arrays.copyOf(blockChain, blockChain.length - 1);
        }

        return blockChain;
    }
}
