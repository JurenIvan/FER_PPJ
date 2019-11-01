package lexer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@SuppressWarnings("javadoc")
public class Utils {

	public static <T> void serializeObject(String name, T object) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
		oos.writeObject(object);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserializeObject(String name) {
		T deserializedObject = null;
		try {
			FileInputStream fis = new FileInputStream(name);
			ObjectInputStream ois = new ObjectInputStream(fis);
			deserializedObject = (T) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return null;
		}

		return deserializedObject;
	}
}
