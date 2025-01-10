/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Carlos Lozano, Marc Llobera
 */
public class Carta extends JLabel {

    private final Pal pal;
    private final int num;
    public JLabel carta;
    public static final int[] tamanyCartes = { 62, 80 };

    // CONSTRUCTOR
    public Carta(Pal pal, int i) {
        this.pal = pal;
        this.num = i;
        carta = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("Cartes/" + i + "_of_" + Pal.nomPal(pal) + ".png"));
            Image imatge = bufferedImage.getScaledInstance(tamanyCartes[0], tamanyCartes[1], Image.SCALE_DEFAULT);
            carta.setIcon(new ImageIcon(imatge));
            carta.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        } catch (IOException ex) {
            System.err.print("La imatge de la carta: " + num + " de " + pal
                    + ", no s'ha pogut afegir correctament.");
        }
    }

    // CONSTRUCTOR CARTA BUIDA
    public Carta(boolean visible) {
        this.pal = null;
        this.num = 0;
        carta = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("Cartes/fondo_casella.png"));
            Image imatge = bufferedImage.getScaledInstance(tamanyCartes[0], tamanyCartes[1], Image.SCALE_DEFAULT);
            carta.setIcon(new ImageIcon(imatge));
            carta.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            carta.setVisible(visible);
        } catch (IOException ex) {
            System.err.print(ex.toString());
        }
    }

    @Override
    public String toString() {
        if (num <= 10) {
            if (num == 1) {
                return "[Carta: As, Pal:" + pal + "]";
            }
            return "[Carta: " + num + ", Pal:" + pal + "]";
        } else {
            switch (num) {
                case 11:
                    return "[Carta: J, Pal:" + pal + "]";
                case 12:
                    return "[Carta: Q, Pal:" + pal + "]";
                case 13:
                    return "[Carta: K, Pal:" + pal + "]";
            }
        }
        return "ERROR";
    }

    public int getNum() {
        return num;
    }

    public Pal getPal() {
        return pal;
    }

}
