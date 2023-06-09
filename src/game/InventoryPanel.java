package game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class InventoryPanel extends JPanel implements MouseListener, MouseMotionListener {
    HousePanel hp;
    Point location;
    MouseEvent pressed;
    InventorySlotPanel inventorySlot;
    JButton closeButton;
    JButton taruhItemButton;
    public InventoryPanel(HousePanel hp){
        this.hp = hp;
        setBounds(hp.centerPanel.getWidth()/4, hp.centerPanel.getHeight()/4, 10*hp.unitSize,8*hp.unitSize);
        setPreferredSize(new Dimension(10*hp.unitSize, 8*hp.unitSize));
        setLocation(hp.centerPanel.getWidth()/4, hp.centerPanel.getHeight()/4);
        setLayout(null);
        setDoubleBuffered(true);
        setBorder(new LineBorder(Color.BLACK, 4, false));
        setBackground(new Color(150, 178, 102));
        addMouseMotionListener(this);
        addMouseListener(this);
        JLabel inventoryTitle = new JLabel("Inventaris");
        inventoryTitle.setForeground(new Color(51, 102, 0));
        inventoryTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        inventoryTitle.setVerticalTextPosition(JLabel.CENTER);
        inventoryTitle.setHorizontalTextPosition(JLabel.CENTER);
        int titleHeight = inventoryTitle.getFontMetrics(inventoryTitle.getFont()).getHeight();
        inventoryTitle.setBounds((getWidth()-inventoryTitle.getFontMetrics(inventoryTitle.getFont()).stringWidth(inventoryTitle.getText()))/2,
                5, 10*hp.unitSize, titleHeight);
        inventorySlot = new InventorySlotPanel(hp, this);
        inventorySlot.setPreferredSize(new Dimension(5*hp.unitSize, 5*hp.unitSize));
        inventorySlot.setBounds(5*hp.unitSize/2, titleHeight+5, 5* hp.unitSize, 5*hp.unitSize);
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        closeButton.setFocusable(false);
        closeButton.setPreferredSize(new Dimension(
                closeButton.getFontMetrics(closeButton.getFont()).stringWidth("Close")+ 80,
                closeButton.getFontMetrics(closeButton.getFont()).getHeight()+30
        ));
        Rectangle closeButtonBounds = new Rectangle(
                (this.getWidth()-(closeButton.getFontMetrics(closeButton.getFont()).stringWidth("Close")+80))/2, inventorySlot.getY()+inventorySlot.getHeight()+5,
                closeButton.getFontMetrics(closeButton.getFont()).stringWidth("Close")+ 80,
                closeButton.getFontMetrics(closeButton.getFont()).getHeight()+30);
        closeButton.setBounds(closeButtonBounds);
        closeButton.setBackground(Color.white);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hp.centerPanel.remove(InventoryPanel.this);
            }
        });

        add(inventoryTitle);
        add(inventorySlot);
        add(closeButton);
        setOpaque(true);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = e;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressed == null) {
            System.out.println("null");
            return;
        }
        Component component = e.getComponent();
        location = component.getLocation();
        int dx = (int) (location.x - pressed.getX() + e.getX());
        int dy = (int) (location.y - pressed.getY() + e.getY());
        component.setLocation(dx, dy);
        component.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
