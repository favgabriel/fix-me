import java.io.FileNotFoundException;
import java.util.Formatter;

public class test {
    static Formatter x=null;
    public static void main(String[] args) {
        for (int i=0; i<=5; i++){
            try {
                System.out.println("fetching");
                x = new Formatter("C:/fd/new"+i);
                System.out.println("writing");
                x.format("%s",i);
                System.out.println("closing");
                x.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
