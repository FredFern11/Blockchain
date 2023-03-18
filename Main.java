package blockchain;

import java.io.File;
import java.io.IOException;

import static blockchain.Miner.*;


public class Main extends Thread{

    public volatile static int zeros = 0;

    public static String extension = "/Users/fredericfernandez/Desktop/Blockchain/Blockchain/task/";
    public static String storageRaw = "storage.data";
    public static String storagePath = extension + storageRaw;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        File storage = new File(storagePath);

        if (!storage.exists()) {
            storage.createNewFile();
            Storage.Serialize(Blockchain.blockChainInfo,storagePath);
        }

        Blockchain.blockChainInfo = (String[][]) Storage.Deserialize(storagePath);
        Blockchain.VerifBlockchain();

        // blockchain verified --> miner's time
        GenMiner(10);
        run = true;

        Verification verification = new Verification();
        verification.start();

//        Change blockChainInfo[blockChainInfo.length-1] --> blockChainInfo[i]
//        Change Thread.sleep for something more logical
//        Random random = new Random();
//        Thread.sleep(2000);
//        System.out.println(System.lineSeparator());
//        for (int i=1; i<6; i++) {
//            System.out.println("Block:\nCreated by miner # " + blockChainInfo[i][6] + "\n" + "Id: " + blockChainInfo[i][0] + "\n" +
//                    "Timestamp: " + blockChainInfo[i][1] + "\n" + "Magic number: " + blockChainInfo[i][5] + "\n"
//                    + "Hash of the previous block:\n" + blockChainInfo[i][3] + "\n" + "Hash of the block:\n" + blockChainInfo[i][4] +
//                    "\n" + "Block data:\n" + blockChainInfo[i][2] + "\nBlock was generating for: " + random.nextInt(10000) + " seconds" + "\n" + random.nextInt(10000) + "\n");
//        }
//        System.exit(1);

        }
}