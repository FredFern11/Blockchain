package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static blockchain.Blockchain.*;

//https://mkyong.com/java/java-asymmetric-cryptography-example/
//https://www.mkyong.com/java/java-digital-signatures-example/

public class Statement {

    private final static String privateKey = "MIICWwIBAAKBgGmhlraom3IWNb5xBcd5zTCxCR+hssiN9feHdiA0rEFEm9C4pwe7";
    private static int id = 0;
    private List<byte[]> list;

    public static int GenId() {
        Random random = new Random();
        id += random.nextInt(100);
        return id;
    }

    public static ArrayList MessageGen() {
        // [0-id, 1-message, 2-signature]
        ArrayList message = new ArrayList();
        message.add(GenId());

        switch (blockChainInfo.length) {
            case 1:
                message.add("Tom: Hey, I'm first!");
                break;

            case 2:
                message.add("Tom: Hey, I'm second also!");
                break;

            case 3:
                message.add("Sarah: It's not fair!\n" + "Sarah: You always will be first because it is your blockchain!\n" + "Sarah: Anyway, thank you for this amazing chat.");
                break;

            case 4:
                message.add("Tom: You're welcome :)\n" + "Nick: Hey Tom, nice chat");
                break;

            case 5:
                message.add("End of conversation");
                break;
        }
        message.add(privateKey);
        return message;
    }

}
