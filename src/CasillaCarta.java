/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;

/**
 *
 * @author Carlos Lozano, Marc Llobera
 */
public class CasillaCarta extends JLabel {

    // CLASSE PER LES CASELLES INFERIORS DE LES CARTES DEL TAULER
    // CONSTRUCTORS
    public CasillaCarta() {
        setBorder(javax.swing.BorderFactory.createLineBorder((new Color(0, 110, 0)), 2));
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 7));
        setBackground(new Color(0, 82, 0));
        setOpaque(true);
    }

    public CasillaCarta(JLabel component) {
        setBorder(javax.swing.BorderFactory.createLineBorder((new Color(0, 110, 0)), 2));
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 7));
        setBackground(new Color(0, 82, 0));
        setOpaque(true);
        add(component);
    }
}
