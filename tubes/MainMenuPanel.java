import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    GridBagConstraints gbc;
    MainMenuPanel(){
        setLayout(null);
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx=0;
//        gbc.gridy=1;
//        gbc.fill= GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.insets = new Insets(50,0,0,0);
        MenuPanel menuPanel = new MenuPanel();
        this.add(menuPanel);
        setPreferredSize(new Dimension(640, 640));

    }
    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.black);
        g2D.setFont(new Font("Comic Sans", Font.BOLD, 80));
        g2D.drawString("Sim-Plicity", 120, 120);
    }
}