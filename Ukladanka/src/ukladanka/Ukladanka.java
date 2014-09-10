package ukladanka;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Ukladanka extends JFrame implements ActionListener {

    private JPanel cPanel;
    private JButton przycisk;
    private JLabel label;
    private Image zrodlo;
    private Image obrazek;
    int[][] poz;
    int szerokosc, wysokosc;

    public Ukladanka() {

        startUI();
    }

    public final void startUI() {

        poz = new int[][]{
                    {0, 1, 2},
                    {3, 4, 5},
                    {6, 7, 8},
                    {9, 10, 11}
                };


        cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(4, 4, 0, 0));

        ImageIcon obraz = new ImageIcon(Ukladanka.class.getResource("Desert.jpg"));
        zrodlo = obraz.getImage();

        szerokosc = obraz.getIconWidth();
        wysokosc = obraz.getIconHeight();


        add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);
        add(cPanel, BorderLayout.CENTER);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 2 && i == 3) {
                    label = new JLabel("");
                    cPanel.add(label);
                } else {
                	przycisk = new JButton();
                    przycisk.addActionListener(this);
                    cPanel.add(przycisk);
                    obrazek = createImage(new FilteredImageSource(zrodlo.getSource(),
                            new CropImageFilter(j * szerokosc / 3, i * wysokosc / 4,
                            (szerokosc / 3) + 1, wysokosc / 4)));
                    przycisk.setIcon(new ImageIcon(obrazek));
                }
            }
        }

        setSize(450, 350);
        setTitle("Uk�adanka");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        JButton przycisk = (JButton) e.getSource();
        Dimension rozmiar = przycisk.getSize();

        int labelX = label.getX();
        int labelY = label.getY();
        int przyciskX = przycisk.getX();
        int przyciskY = przycisk.getY();
        int przyciskPozX = przyciskX / rozmiar.width;
        int przyciskPozY = przyciskY / rozmiar.height;
        int przyciskIndex = poz[przyciskPozY][przyciskPozX];



        if (labelX == przyciskX && (labelY - przyciskY) == rozmiar.height) {

            int labelIndex = przyciskIndex + 3;

            cPanel.remove(przyciskIndex);
            cPanel.add(label, przyciskIndex);
            cPanel.add(przycisk, labelIndex);
            cPanel.validate();
        }

        if (labelX == przyciskX && (labelY - przyciskY) == -rozmiar.height) {

            int labelIndex = przyciskIndex - 3;
            cPanel.remove(labelIndex);
            cPanel.add(przycisk, labelIndex);
            cPanel.add(label, przyciskIndex);
            cPanel.validate();
        }

        if (labelY == przyciskY && (labelX - przyciskX) == rozmiar.width) {

            int labelIndex = przyciskIndex + 1;

            cPanel.remove(przyciskIndex);
            cPanel.add(label, przyciskIndex);
            cPanel.add(przycisk, labelIndex);
            cPanel.validate();
        }

        if (labelY == przyciskY && (labelX - przyciskX) == -rozmiar.width) {

            int labelIndex = przyciskIndex - 1;

            cPanel.remove(przyciskIndex);
            cPanel.add(label, labelIndex);
            cPanel.add(przycisk, labelIndex);
            cPanel.validate();
        }
    }

    public static void main(String[] args) {
        new Ukladanka().setVisible(true);
 }
}
