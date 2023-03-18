package blockchain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static blockchain.Blockchain.*;
import static blockchain.Miner.run;

public class Verification extends Thread {

    public void run() {

        int blockNumber = 0;
        long presentTime;
        long duration;

        while (blockNumber >= 0) {

            run = true;

            if (blockChainWaitList.length == 3) {
                blockChainWaitList = new String[1][7];
                blockChainWaitList[0] = new String[]{"0", "0", "0", "0", "0", "0", "0"};
            }
            while (blockNumber > blockChainWaitList.length-1) {}

            if (blockChainWaitList[blockNumber] == null) {}

            else if (
                    Integer.parseInt(blockChainWaitList[blockNumber][0]) == (Integer.valueOf(blockChainInfo[blockChainInfo.length-1][0]) + 1)
                    && Long.valueOf(blockChainWaitList[blockNumber][1]) >= Long.valueOf(blockChainInfo[blockChainInfo.length-1][1])
                    && blockChainWaitList[blockNumber][3].equals(blockChainInfo[blockChainInfo.length-1][4])
                    && Miner.GenSha256(blockChainWaitList[blockNumber][2] + blockChainWaitList[blockNumber][5]).equals(blockChainWaitList[blockNumber][4])
                    && Integer.valueOf(blockChainWaitList[blockNumber][0]) == blockChainInfo.length
            ) {

                run = false;

                blockChainInfo = Arrays.copyOf(blockChainInfo, blockChainInfo.length + 1);
                blockChainInfo[blockChainInfo.length-1] = blockChainWaitList[blockNumber];

                presentTime = new Date().getTime();
                duration = (presentTime - Long.valueOf(blockChainWaitList[blockNumber][1]))/1000;
                String zeroString = ChangeZeros(duration);
                blockNumber = 0;

//              Blockchain.blockChainWaitList = {0-id, 1-timeStamps, 2-input, 3-hashPrev, 4-hashBlock, 5-magicNumber, 6-threadId}
                System.out.println("Block:\nCreated by miner # " + blockChainInfo[blockChainInfo.length-1][6] + "\n" + "Id: " + blockChainInfo[blockChainInfo.length-1][0] + "\n" +
                        "Timestamp: " + blockChainInfo[blockChainInfo.length-1][1] + "\n" + "Magic number: " + blockChainInfo[blockChainInfo.length-1][5] + "\n"
                        + "Hash of the previous block:\n" + blockChainInfo[blockChainInfo.length-1][3] + "\n" + "Hash of the block:\n" + blockChainInfo[blockChainInfo.length-1][4] +
                        "\n" + "Block data:\n" + blockChainInfo[blockChainInfo.length-1][2] + "\nBlock was generating for: " + duration + " seconds" + "\n" + zeroString + "\n");

                blockChainWaitList = new String[1][7];
                blockChainWaitList[0] = new String[]{"0", "0", "0", "0", "0", "0", "0"};

                try {
                    VerifBlockchain();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Storage.Serialize(blockChainInfo, "storage.data");
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                To only print 5 blocks
                if (blockChainInfo.length > 5) {
                    File storage = new File("/Users/fredericfernandez/Desktop/Blockchain/Blockchain/task/storage.data");
                    storage.delete();
                    System.exit(1);
                }
            }
            else {
                while (blockNumber > blockChainWaitList.length) {}
                blockNumber++;
            }


        }
    }


}
