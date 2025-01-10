/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos Lozano, Marc Llobera
 */
public enum Pal {
    TREBOLS, DIAMANTS, CORS, PIQUES;

    public static String nomPal(Pal pal) {
        switch (pal) {
            case TREBOLS:
                return "clubs";
            case DIAMANTS:
                return "diamonds";
            case CORS:
                return "hearts";
            case PIQUES:
                return "spades";
        }
        return null;
    }
}
