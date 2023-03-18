package blockchain;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static blockchain.Blockchain.*;
import static blockchain.Statement.*;

public class Miner extends Thread{

    public volatile static boolean run = false;

    public static String GenSha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void GenMiner(int quantity) {
        for (int i=0; i<quantity; i++) {
            Miner miner = new Miner();
            miner.start();
        }
    }

    public static String[] CreateBlock(String input) {
        Random random = new Random();

        int id = blockChainInfo.length;
        long timeStamp = new Date().getTime();
        String hashPrev = String.valueOf(blockChainInfo[blockChainInfo.length-1][4]);
        String hashBlock;
        String threadId = String.valueOf(Thread.currentThread().getId());

        int magicNumber = 0;
        int i = 0;

        hashBlock = GenSha256(input + magicNumber);

        while (i <= Main.zeros-1) {
            if (hashBlock.charAt(i) == '0') {
                i++;
            } else {
                i = 0;
                magicNumber = random.nextInt(10000000);
                hashBlock = GenSha256(input + magicNumber);
            }
        }

        return new String[]{String.valueOf(id), String.valueOf(timeStamp), input, hashPrev, hashBlock, String.valueOf(magicNumber), threadId};
    }

    public void run() {
        while (1<2) {
            if (run) {
                String dataBlock = (String) MessageGen().get(1);
                String[] block = CreateBlock(dataBlock);

                if ((block != null) && (block[0].equals(String.valueOf(Integer.valueOf(blockChainInfo[blockChainInfo.length - 1][0]) + 1))) && blockChainWaitList.length < 5) {
                    InsertMinedBlock(block);
                    run = false;
                }
            }
        }

    }

    private static synchronized void InsertMinedBlock(String[] block) {
            blockChainWaitList = Arrays.copyOf(blockChainWaitList, blockChainWaitList.length + 1);
//        Blockchain.blockChainWaitList = {0-id, 1-timeStamps, 2-input, 3-hashPrev, 4-hashBlock, 5-magicNumber, 6-threadId}
            blockChainWaitList[blockChainWaitList.length-1] = block;
    }
}
