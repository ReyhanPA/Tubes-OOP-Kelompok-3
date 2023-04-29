package game;

import entity.Rumah;
import entity.Sim;
import data.*;
import entity.World;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class WorldOptionPanel extends JPanel implements ActionListener {
    public JButton toMainMenuButton = new JButton("Back to Main Menu");
    public JButton addHouseButton = new JButton("Add Sim");
    public JButton changeSimButton = new JButton("Change Sim");
    public JButton viewCurrentLocButton = new JButton("View Current Location");
    public JButton saveButton = new JButton("Save");
    public JSlider volumeSlider = new JSlider(500, 860, 700);
    public JLabel timeLabel;
    public MainPanel mp;
    public WorldPanel wp;
    public World loadedWorld;
    public WorldOptionPanel(MainPanel mainPanel, WorldPanel worldPanel){
        super(new BorderLayout());
        mp = mainPanel; wp = worldPanel;
//        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        loadedWorld = wp.getWorld();
        Dimension size = new Dimension(170, wp.getHeight()/3+70);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setBounds(wp.getWidth() - this.getWidth() - 10,10, 170, wp.getHeight()/3+70);
        this.setBackground(Color.white);
        setFocusable(false);

        JPanel main = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        main.setBackground(Color.white);
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.white);
        panel1.setPreferredSize(new Dimension(170,50));

        timeLabel = new JLabel("<html> "+ wp.getWorld().getWaktu().tampilkanWaktu()[0] +"<br>" +
                wp.getWorld().getWaktu().tampilkanWaktu()[1] + "<br>" +
                wp.getWorld().getWaktu().tampilkanWaktu()[2] + "</html>");
//        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
        timeLabel.setFocusable(false);
//        timeLabel.setOpaque(true);
        timeLabel.setVerticalTextPosition(SwingConstants.CENTER);
        timeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.white);
        GridLayout layout1 = new GridLayout(5,1);
        layout1.setVgap(5);
        panel2.setLayout(layout1);
        
        toMainMenuButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));             // done
        toMainMenuButton.addActionListener(this);                                                 // done
        toMainMenuButton.setBackground(Color.yellow);
        toMainMenuButton.setPreferredSize(new Dimension(100,25));

        panel1.add(timeLabel);
        main.add(panel1);

        addHouseButton.addActionListener(this);                                                   // done
        addHouseButton.setFocusable(false);                                             // done
        addHouseButton.setHorizontalTextPosition(JButton.CENTER);                                 // done
        addHouseButton.setVerticalTextPosition(JButton.CENTER);                                   // done
        addHouseButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));               // done

        changeSimButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        changeSimButton.setVerticalTextPosition(JButton.CENTER);
        changeSimButton.setHorizontalTextPosition(JButton.CENTER);
        changeSimButton.setFocusable(false);

        viewCurrentLocButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        viewCurrentLocButton.setVerticalTextPosition(JButton.CENTER);
        viewCurrentLocButton.setHorizontalTextPosition(JButton.CENTER);
        viewCurrentLocButton.setFocusable(false);
        viewCurrentLocButton.addActionListener(this);

        saveButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        saveButton.setVerticalTextPosition(JButton.CENTER);
        saveButton.setHorizontalTextPosition(JButton.CENTER);
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);

        panel2.add(toMainMenuButton);
        panel2.add(addHouseButton);
        panel2.add(changeSimButton);
        panel2.add(viewCurrentLocButton);
        panel2.add(saveButton);
        main.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.white);
        panel3.setPreferredSize(new Dimension(160,50));

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = (float) volumeSlider.getValue()/10f;
                if (!(volume < 61f)){
                    wp.sound.setVolume(-80f+volume);
                }
                else{
                    wp.sound.setVolume(-86f);
                }
            }
        });
        JLabel music = new JLabel("Music Volume");
        music.setFont(new Font("Courier New", Font.BOLD, 10));
        music.setHorizontalTextPosition(SwingConstants.CENTER);
        music.setBackground(Color.white);
        music.setOpaque(true);
        volumeSlider.setPreferredSize(new Dimension(160,20));

        panel3.add(music);
        panel3.add(volumeSlider);
        main.add(panel3);

        add(main, BorderLayout.CENTER);
        wp.wop = this;
    }

    private int x;
    private int y;
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toMainMenuButton){

            JPanel warningMessage = new JPanel(new GridLayout(0,1));
            JLabel label = new JLabel("Pastikan bahwa file data telah disimpan.");
            JLabel label2 = new JLabel("Yakin ingin keluar?");
            warningMessage.add(label);warningMessage.add(label2);
            int result = JOptionPane.showConfirmDialog(null, warningMessage, "Reminder"
                    , JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.OK_OPTION){
                System.out.println("Exit Game");
                wp.stopMusic();
                wp.mainThread.interrupt();
                wp.mainThread =null;
                mp.remove(toMainMenuButton);
                mp.add(wp.mmp, BorderLayout.CENTER);
                mp.remove(this);
                mp.remove(wp);
                mp.revalidate();
                mp.repaint();
            }

        } else if (e.getSource() == addHouseButton){
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JLabel label = new JLabel("<html>Set the coordinate of your house:<br></html>");
            JTextField xField = new JTextField();
            ((AbstractDocument)xField.getDocument()).setDocumentFilter(new IntegerFilter());
            JTextField yField = new JTextField();
            ((AbstractDocument)yField.getDocument()).setDocumentFilter(new IntegerFilter());
            JButton colorButton = new JButton("Choose Color");
            colorButton.setFocusable(false);
            final Color[] color = new Color[1];
            colorButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color chosenColor = JColorChooser.showDialog(null, "Choose a color", Color.white);
                    color[0] = chosenColor;
                }
            });
            ArrayList<Sim> daftarSim = wp.getWorld().getDaftarSim();
            String[] listNamaSim = new String[daftarSim.size()];
            for (int i = 0; i < listNamaSim.length; i++){
                listNamaSim[i] = daftarSim.get(i).getNamaLengkap();
            }
            // masukan nama sim
            JTextField namaSimField = new JTextField();
            panel.add(new JLabel("<html>Masukkan Nama Sim"));
            panel.add(namaSimField);
            panel.add(label);
            panel.add(new JLabel("<html>X Coordinate:<br></html>"));
            panel.add(xField);
            panel.add(new JLabel("<html>Y Coordinate:<br></html>"));
            panel.add(yField);
            panel.add(new JLabel("<html>Color:<br></html>"));
            panel.add(colorButton);
            int result = JOptionPane.showConfirmDialog(null, panel, "Tambah Sim",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String namaSim = namaSimField.getText();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                // cek apakah nama sim, dan koordinat sudah dipakai sudah ada
                boolean hasSim = false;
                boolean occupied = false;
                for (Sim sim: wp.getWorld().getDaftarSim()){
                    if (sim.getNamaLengkap().equals(namaSim)){
                        hasSim = true;
                        break;
                    }
                }
                for (Rumah rumah:wp.getWorld().getDaftarRumah()){
                    if (rumah.getLokasi().equals(new Point(x,y))){
                        occupied = true;
                    }
                }
                if (hasSim){
                    /* add popup that show message that the sim Name has already been taken */
                    JOptionPane.showMessageDialog(null, "The Sim name has already been taken.");
                }
                else if (occupied){
                    /* add popup that show message that the place is occupied */
                    JOptionPane.showMessageDialog(null, "The position is already occupied.");
                }
                else if (wp.getWorld().isLimitSimCreation()){
                    JOptionPane.showMessageDialog(null, "Anda hanya bisa membuat Sim 1 kali sehari");
                }
                else{
                    Sim simBaru = new Sim(namaSim, wp.getWorld());
                    Rumah rumahBaru = new Rumah(x, y,simBaru, color[0],wp.getWorld());
                    wp.getWorld().tambahRumah(rumahBaru);
                    wp.getWorld().tambahSim(simBaru);
                }
            }
        } else if (e.getSource() == saveButton) {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField namaFile = new JTextField();

            panel.add(new JLabel("<html>Masukkan Nama File Yang Ingin Disimpan"));
            panel.add(namaFile);
            int result = JOptionPane.showConfirmDialog(null, panel, "Save",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String saveFile = namaFile.getText();
                SaveLoad saveLoad = new SaveLoad();
                saveLoad.save(saveFile, loadedWorld);
            }

        } else if (e.getSource() == viewCurrentLocButton) {
            if (wp.viewCurrentLocationPanel != null){
                wp.remove(wp.viewCurrentLocationPanel);
                wp.viewCurrentLocationPanel = null;
                return;
            }
            JPanel panel = new JPanel(new GridLayout(0,1));
            panel.setBackground(new Color(150, 178, 102));
            JLabel titleLabel = new JLabel("LOKASI SIM SAAT INI");
            JLabel lineLabel = new JLabel("-------------------------------------------");
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel rumahLocLabel = new JLabel(" Rumah    : (" + (int) loadedWorld.getChosenSim().getLocRuang().getInfoRumah().getLokasi().getX() + ", " + (int) loadedWorld.getChosenSim().getLocRuang().getInfoRumah().getLokasi().getY() + ")");
            JLabel ruanganLocLabel = new JLabel(" Ruangan  : " + loadedWorld.getChosenSim().getLocRuang().getNama());
            panel.add(titleLabel);
            panel.add(lineLabel);
            panel.add(rumahLocLabel);
            panel.add(ruanganLocLabel);
            panel.setOpaque(true);
            panel.setBorder(new LineBorder(Color.BLACK, 3, true));
            panel.setBounds(wp.getMapX()+(wp.getWidth()-200)/2, wp.getMapY()+(wp.getHeight()-100)/2, 200, 100);
            panel.setFocusable(false);
            wp.viewCurrentLocationPanel = panel;
            wp.add(panel);
            wp.revalidate();
            wp.repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RoundRectangle2D shape = new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), 10, 10);
        g2d.setClip(shape);
    }
}