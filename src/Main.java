import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String ar[]) throws IOException {
        print();
        File input = new File("input.txt");
        TuringMachine TM = new TuringMachine(input);
        TM.sim();
    }

    private static void print() {

    }

}
