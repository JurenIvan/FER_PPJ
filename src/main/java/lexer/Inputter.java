package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inputter {

    public String outputString() {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            sb.append(sc.nextLine());
            if(sc.hasNext()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public List<String> outputListString() {
        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            list.add(sc.nextLine());
        }
        return list;
    }
}
