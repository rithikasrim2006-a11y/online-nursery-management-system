package onlinenursery;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            NurseryRegister reg = new NurseryRegister();
            reg.rg();
        });
    }
}
