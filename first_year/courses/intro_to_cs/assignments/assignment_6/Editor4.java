import java.awt.Color;

public class Editor4 {
    public static void main (String[] args){
    String image = args[0];
    int steps = Integer.parseInt(args[1]);
    Color[][] imaggray = Instush.greyscaled(Instush.read(image));
    Color[][] imageM = Instush.read(image);
    Instush.morph( imaggray, imageM, steps);
    }
}
