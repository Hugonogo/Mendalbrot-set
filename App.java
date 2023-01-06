import java.awt.*;
import java.awt.event.*;
public class App extends Frame {
    public static void main(String[] args) throws Exception {
        new App();
    }
    App(){
        super("janela");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
        setSize(800, 600);
        add("Center", new CanvasM());
        show();
    }
}
