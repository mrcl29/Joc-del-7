/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Carlos Lozano, Marc Llobera
 */
public class Jugador {

    // ATRIBUTS
    public final ArrayList<Carta> cartasAsignadas;

    // CONSTRUCTOR
    public Jugador() {
        cartasAsignadas = new ArrayList<>();
    }

    public void asignarCarta(Carta carta) {
        cartasAsignadas.add(carta);
    }

    public void eliminarCarta(Carta carta) {
        cartasAsignadas.remove(carta);
    }

    public int getNumCartas() {
        return cartasAsignadas.size();
    }

    public ArrayList<Carta> getCartasAsignadas() {
        return cartasAsignadas;
    }

    public Carta treureCarta(Tauler taula) {
        for (int i = 0; i < cartasAsignadas.size(); i++) {
            if (taula.colocarCarta(cartasAsignadas.get(i))) {
                Carta c = cartasAsignadas.get(i);
                cartasAsignadas.remove(i);
                return c;
            }
        }
        return null;
    }
}
