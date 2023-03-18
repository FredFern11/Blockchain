package blockchain;

import java.io.*;

public class Storage {

    private static final long serialVersionUID = 1L;

    public static void Serialize(Object object, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(object);
        oos.close();
    }

    public static Object Deserialize(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Object object = ois.readObject();
        ois.close();
        return object;
    }

}
