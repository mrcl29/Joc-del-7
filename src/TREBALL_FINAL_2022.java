
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

/**
 *
 * @author Carlos Lozano, Marc Llobera
 *         Video: https://youtu.be/snmbwsmNoxY
 */
public class TREBALL_FINAL_2022 extends JFrame {

    // ATRIBUTS INTERFICIE
    private Container contenedor;
    private final Color colorTauler = new Color(0, 110, 0);
    private final JLabel[] panelCartesRestantsIA = new JLabel[3];
    private final JPanel taulerBaralla = new JPanel();
    private final JPanel taulerUsuari = new JPanel();
    private JPanel menuBotons;
    private JButton mescla, juga, reinicia, passa, tornJugador;
    private final JPanel menuTotal = new JPanel();
    private final JTextArea texteMissatge = new JTextArea();
    private final JSplitPane separadorIA = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private final JSplitPane separadorTablero = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private final JSplitPane separadorMenu = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    // ATRIBUTS JOC
    private final Jugador[] jugadorsIA = new Jugador[3];
    private Jugador jugadorUsuari = new Jugador();
    private final Carta[] cartesUsuari = new Carta[13];
    private boolean acabat;
    public Tauler tauler;
    private Baralla baralla;
    private int torn = 3;// els torns van del 0 al 3(torn usuari)

    public static void main(String[] args) throws IOException {
        new TREBALL_FINAL_2022().interfici();
    }

    private void interfici() throws IOException {
        setTitle("Pràctica Prog II - Joc del 7");
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1000, 680);
        setResizable(false);
        setDefaultCloseOperation(TREBALL_FINAL_2022.EXIT_ON_CLOSE);
        contenedor = getContentPane();

        /*----------------------------------------------------------------------
        --TABLERO DE JUEGO
        ----------------------------------------------------------------------*/
        //////////////////////////////JUGADORS IA///////////////////////////////
        panelCartesRestantsIA[0] = new JLabel();
        actualitzarMaJugadorIA(0, "fondo_casella", panelCartesRestantsIA[0]);
        jugadorsIA[0] = new Jugador();
        panelCartesRestantsIA[1] = new JLabel();
        actualitzarMaJugadorIA(0, "fondo_casella", panelCartesRestantsIA[1]);
        jugadorsIA[1] = new Jugador();
        panelCartesRestantsIA[2] = new JLabel();
        actualitzarMaJugadorIA(0, "fondo_casella", panelCartesRestantsIA[2]);
        jugadorsIA[2] = new Jugador();

        // agrupam les mans e cartes dins un panell
        JPanel taulerJugadorsIA = new JPanel();
        taulerJugadorsIA.setBackground(colorTauler);
        taulerJugadorsIA.setLayout(new GridLayout(1, 5, 135, 0));

        taulerJugadorsIA.add(new Carta(false));
        taulerJugadorsIA.add(panelCartesRestantsIA[0]);
        taulerJugadorsIA.add(panelCartesRestantsIA[1]);
        taulerJugadorsIA.add(panelCartesRestantsIA[2]);
        taulerJugadorsIA.add(new JLabel());

        ///////////////////////////TAULER - BARALLA/////////////////////////////
        taulerBaralla.setBackground(colorTauler);
        taulerBaralla.setLayout(new GridLayout(4, 13));
        tauler = new Tauler();
        baralla = new Baralla();
        mostrarTaulerMesclat(false);// mostram el tauler sense mesclar

        ////////////////////////////JUGADOR USUARI//////////////////////////////
        // cream les cartes buides del jugador Usuari
        cartesUsuari[0] = new Carta(true);
        for (int i = 1; i < cartesUsuari.length; i++) {
            cartesUsuari[i] = new Carta(false);
        }
        taulerUsuari.setBackground(colorTauler);
        taulerUsuari.setLayout(new GridBagLayout());
        actualitzarMaJugadorUsuari(0);// actualitzar mà amb les ncartes del usuari
        ////////////////////////////////////////////////////////////////////////

        /*----------------------------------------------------------------------
        --MENU INFERIOR
        ----------------------------------------------------------------------*/
        ////////////////////GESTIÓ D'EVENTS DELS BOTONS/////////////////////////
        ActionListener gestorEventos = (ActionEvent evento) -> {
            switch (evento.getActionCommand()) {
                case "Mescla": {
                    // mostram el tauler amb cartes mesclades
                    mostrarTaulerMesclat(true);
                    juga.setEnabled(true);// activam botó Jugar
                    reinicia.setEnabled(true);// activam botó Reiniciar
                    break;
                }
                case "Juga": {
                    iniciJoc();// cridam per iniciar el joc
                    // cambiam els botons del menú
                    mostrarMenuCorresponent(passa, null);
                    break;
                }
                case "Reinicia": {
                    // reiniciam les variables del joc per tornar a començar
                    jugadorsIA[0] = new Jugador();
                    jugadorsIA[1] = new Jugador();
                    jugadorsIA[2] = new Jugador();
                    jugadorUsuari = new Jugador();
                    actualitzarMaJugadorIA(0, "fondo_casella",
                            panelCartesRestantsIA[0]);
                    actualitzarMaJugadorIA(0, "fondo_casella",
                            panelCartesRestantsIA[1]);
                    actualitzarMaJugadorIA(0, "fondo_casella",
                            panelCartesRestantsIA[2]);
                    tauler = new Tauler();
                    baralla = new Baralla();
                    // mostram el tauler sense mesclar
                    mostrarTaulerMesclat(false);
                    cartesUsuari[0] = new Carta(true);
                    for (int i = 1; i < cartesUsuari.length; i++) {
                        cartesUsuari[i] = new Carta(false);
                    }
                    // actualitzar mà amb les ncartes del usuari
                    actualitzarMaJugadorUsuari(0);
                    juga.setEnabled(false);
                    reinicia.setEnabled(false);
                    passa.setEnabled(true);
                    tornJugador.setEnabled(true);
                    mostrarMenuCorresponent(mescla, juga);
                    texteMissatge
                            .setText("Abans de jugar cal mesclar la baralla");
                    break;
                }
                case "Passa": {
                    torn = 0;// quan passam comença el primer jugadorIA
                    actualitzarText("Has passat");
                    // cambiam els botons del menú
                    mostrarMenuCorresponent(tornJugador, null);
                    break;
                }
                case "Torn Jugador": {
                    // treim una carta del jugadorIA corresponent
                    Carta posada = jugadorsIA[torn].treureCarta(tauler);
                    actualitzarMaJugadorIA(jugadorsIA[torn].getNumCartas(),
                            "card_back_blue", panelCartesRestantsIA[torn]);
                    if (jugadorsIA[torn].getNumCartas() == 0) {
                        // Si el jugador es queda sense cartes acabe el joc
                        acabat = true;
                    }
                    torn++;
                    if (posada != null) {
                        actualitzarText("El Jugador " + torn + " ha posat el "
                                + posada.toString());
                    } else {
                        actualitzarText("El Jugador" + torn + " passa");
                    }
                    // Si es el torn del usuari cambiam botons del menu
                    if (torn == 3) {
                        mostrarMenuCorresponent(passa, null);
                    }
                    // actualitzam el tauler de la interfície amb les cartes del
                    // tauler del joc
                    actualitzarTauler(tauler.taulerCartes);
                    if (acabat) {// acabam la partida
                        partidaAcabada(false, torn - 1);
                        torn = 3;
                        tornJugador.setEnabled(false);
                    }
                    break;
                }

            }
        };

        // BOTONS
        menuBotons = new JPanel();

        mescla = new JButton("Mescla");
        mescla.addActionListener(gestorEventos);
        mescla.setBorder(new RoundedBorder(6));

        juga = new JButton("Juga");
        juga.addActionListener(gestorEventos);
        juga.setBorder(new RoundedBorder(6));
        juga.setEnabled(false);

        reinicia = new JButton("Reinicia");
        reinicia.addActionListener(gestorEventos);
        reinicia.setBorder(new RoundedBorder(6));
        reinicia.setEnabled(false);

        passa = new JButton("Passa");
        passa.addActionListener(gestorEventos);
        passa.setBorder(new RoundedBorder(6));

        tornJugador = new JButton("Torn Jugador");
        tornJugador.addActionListener(gestorEventos);
        tornJugador.setBorder(new RoundedBorder(6));

        mostrarMenuCorresponent(mescla, juga);

        // MISSATGE INFERIOR
        texteMissatge.setFocusable(false);
        actualitzarText("Abans de jugar cal mesclar la baralla");

        menuTotal.setLayout(new GridLayout(2, 1));
        menuTotal.add(menuBotons);
        menuTotal.add(texteMissatge);

        /*----------------------------------------------------------------------
        --DISTRIBUCIÓ
        ----------------------------------------------------------------------*/
        separadorIA.setBackground(colorTauler);
        separadorTablero.setBackground(colorTauler);
        separadorMenu.setBackground(colorTauler);

        separadorIA.setTopComponent(taulerJugadorsIA);

        separadorIA.setBottomComponent(taulerBaralla);

        separadorIA.setDividerSize(0);
        separadorTablero.setTopComponent(taulerBaralla);

        separadorTablero.setBottomComponent(taulerUsuari);

        separadorTablero.setDividerSize(0);
        separadorMenu.setTopComponent(taulerUsuari);

        separadorMenu.setBottomComponent(menuTotal);

        separadorMenu.setDividerSize(1);

        contenedor.add(separadorIA, BorderLayout.NORTH);

        contenedor.add(separadorTablero, BorderLayout.CENTER);

        contenedor.add(separadorMenu, BorderLayout.SOUTH);

        setVisible(true);
    }

    /*--------------------------------------------------------------------------
    --MÈTODES
    --------------------------------------------------------------------------*/
    /**
     * Actualitzam el panell del jugador IA corresponent amb les cartes restants
     * i la imatge a mostrar
     *
     * @param cartesRestants ncartes restants que es mostrará al panell
     * @param x              nom del arxiu imatge que es mostrarà
     * @param aux            panell del Jugador IA que es modifica
     */
    private void actualitzarMaJugadorIA(int cartesRestants, String x,
            JLabel aux) {
        aux.removeAll();
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("Cartes/" + x
                    + ".png"));
            if (bufferedImage != null) {
                Image imatge = bufferedImage.getScaledInstance(
                        Carta.tamanyCartes[0] + 20, Carta.tamanyCartes[1],
                        Image.SCALE_DEFAULT);
                aux.setIcon(new ImageIcon(imatge));
            }
            aux.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
            JTextArea text_Jugador = new JTextArea(String.valueOf(cartesRestants));
            text_Jugador.setForeground(Color.WHITE);
            text_Jugador.setFont(new Font("Arial", Font.CENTER_BASELINE, 55));
            text_Jugador.setOpaque(false);
            text_Jugador.setEditable(false);
            aux.add(text_Jugador);
        } catch (IOException ex) {
            Logger.getLogger(TREBALL_FINAL_2022.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        contenedor.repaint();
    }

    /**
     * Actualitzam el panell del jugador Usuari corresponent amb les cartes
     * restants i totes les cartes que té en aquell moment
     *
     * @param cartesRestants ncartes restants que es mostrarà al panell
     */
    private void actualitzarMaJugadorUsuari(int cartesRestants) {
        taulerUsuari.removeAll();
        separadorTablero.setBottomComponent(taulerUsuari);
        separadorMenu.setTopComponent(taulerUsuari);

        for (int i = 0; i < cartesUsuari.length; i++) {
            if (!jugadorUsuari.getCartasAsignadas().contains(cartesUsuari[i])) {
                if (i == 0) {
                    cartesUsuari[i] = new Carta(true);
                } else {
                    cartesUsuari[i] = new Carta(false);
                }
            }
        }

        GridBagConstraints restricciones = new GridBagConstraints();

        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.anchor = GridBagConstraints.PAGE_END;
        restricciones.insets = new Insets(0, 8, 0, 0);
        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.ipady = 5;
        restricciones.ipadx = 0;
        restricciones.weightx = 0.1;
        restricciones.weighty = 0;

        for (int i = 0; i < 13; i++) {
            taulerUsuari.add(cartesUsuari[i].carta, restricciones);
            restricciones.gridx = restricciones.gridx + 1;
        }

        JTextArea auxx = new JTextArea(String.valueOf(cartesRestants));
        auxx.setLayout(new FlowLayout(FlowLayout.CENTER));
        auxx.setForeground(Color.WHITE);
        auxx.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
        auxx.setOpaque(false);
        auxx.setEditable(false);

        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.ipady = 0;
        restricciones.ipadx = 0;
        restricciones.weighty = 1.0;
        restricciones.insets = new Insets(0, 28, 0, 0);
        restricciones.gridwidth = 2;
        taulerUsuari.add(auxx, restricciones);
        contenedor.repaint();
    }

    /**
     * Actualitzam el tauler mostrant les cartes o ordenades o mesclades
     *
     * @param mezclado indica si es mostrarà el tauler amb les carten en ordre o
     *                 es mesclarà la baralla
     */
    private void mostrarTaulerMesclat(boolean mezclado) {
        taulerBaralla.removeAll();
        separadorIA.setBottomComponent(taulerBaralla);
        separadorTablero.setTopComponent(taulerBaralla);
        if (mezclado) {
            baralla.mescla();
            Carta[] cartesBaralla = baralla.getB();
            int index = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 13; j++) {
                    taulerBaralla.add(
                            new CasillaCarta(cartesBaralla[index].carta));
                    index++;
                }
            }
            actualitzarText("La baralla està mesclada");
        } else {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 13; j++) {
                    taulerBaralla.add(new CasillaCarta(
                            new Carta(Pal.values()[i], j + 1).carta));
                }
            }
        }
        contenedor.repaint();
    }

    /**
     * Actualitzam el tauler mostrant les cartes que ha de tenir en aquell
     * moment
     *
     * @param c matriu de les cartes que es mostren al tauler
     */
    private void actualitzarTauler(Carta[][] c) {
        taulerBaralla.removeAll();
        separadorIA.setBottomComponent(taulerBaralla);
        separadorTablero.setTopComponent(taulerBaralla);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (c[i][j] == null) {
                    taulerBaralla.add(new CasillaCarta());
                } else {
                    taulerBaralla.add(new CasillaCarta(c[i][j].carta));
                }
            }
        }

        contenedor.repaint();
    }

    /**
     * Mètode que inicialitza el joc repartint les cartes a cada jugador
     */
    private void iniciJoc() {
        acabat = false;
        actualitzarTauler(tauler.taulerCartes);
        for (int i = 0; i < Baralla.MAXCARTES / 4; i++) {
            Carta aux = baralla.agafaCarta();

            // Listener del ratolí per les cartes d'Usuari
            aux.carta.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (torn == 3 && !acabat) {
                        if (tauler.colocarCarta(aux)) {
                            jugadorUsuari.eliminarCarta(aux);
                            actualitzarMaJugadorUsuari(jugadorUsuari
                                    .getNumCartas());
                            if (jugadorUsuari.getNumCartas() == 0) {
                                acabat = true;
                            }
                            actualitzarText("Has posat el " + aux.toString());
                            torn++;
                            if (torn == 4) {
                                torn = 0;
                                mostrarMenuCorresponent(tornJugador, null);
                            }
                            actualitzarTauler(tauler.taulerCartes);
                            if (acabat) {
                                torn = 3;
                                passa.setEnabled(false);
                                partidaAcabada(true, 3);
                            }
                        } else {
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
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
            });

            jugadorUsuari.asignarCarta(aux);
            cartesUsuari[i] = aux;
        }

        actualitzarMaJugadorUsuari(jugadorUsuari.getNumCartas());
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < Baralla.MAXCARTES / 4; k++) {
                jugadorsIA[i].asignarCarta(baralla.agafaCarta());
            }
            actualitzarMaJugadorIA(jugadorsIA[i].getNumCartas(),
                    "card_back_blue", panelCartesRestantsIA[i]);
        }
        contenedor.repaint();
        actualitzarText("Les cartes estàn repartides,"
                + " és el teu torn, posa un 7 si el tens");
    }

    /**
     * Actualitzam el menu de botons amb els botons pasats per perámetre
     *
     * @param jb1 primer botó que es mostrarà sempre
     * @param jb2 segon botó que es mostrarà o no depenent de si es null o no
     */
    private void mostrarMenuCorresponent(JButton jb1, JButton jb2) {
        menuBotons.removeAll();
        menuBotons = new JPanel();
        menuBotons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 3));
        if (jb2 != null) {
            menuBotons.add(jb1);
            menuBotons.add(jb2);
        } else {
            menuBotons.add(jb1);
        }
        menuBotons.add(reinicia);
        menuTotal.removeAll();
        menuTotal.add(menuBotons);
        menuTotal.add(texteMissatge);
        separadorMenu.setBottomComponent(menuTotal);
        contenedor.repaint();
    }

    /**
     * Mètode per quan acaba la partida mostrar el missatge corresponent per
     * pantalla
     *
     * @param guanyat  indicam si l'usuari ha guanyat o no
     * @param nJugador jugador que ha guanyat la partida per seleccionar la
     *                 imatge a mostrar
     */
    public void partidaAcabada(boolean guanyat, int nJugador) {
        if (guanyat) {
            BufferedImage bufferedImage;
            Image imatge = null;
            try {
                bufferedImage = ImageIO.read(new File("Cartes/Jug" + nJugador
                        + "Riu.png"));
                imatge = bufferedImage.getScaledInstance(Carta.tamanyCartes[0],
                        Carta.tamanyCartes[1], Image.SCALE_DEFAULT);
            } catch (IOException ex) {
                Logger.getLogger(TREBALL_FINAL_2022.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(
                    null,
                    new JLabel("HAS GUANYAT!!!", new ImageIcon(imatge),
                            JLabel.LEFT),
                    "Uep!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            BufferedImage bufferedImage;
            Image imatge = null;
            try {
                bufferedImage = ImageIO.read(new File("Cartes/Jug" + nJugador
                        + "Riu.png"));
                imatge = bufferedImage.getScaledInstance(Carta.tamanyCartes[0],
                        Carta.tamanyCartes[1], Image.SCALE_DEFAULT);
            } catch (IOException ex) {
                Logger.getLogger(TREBALL_FINAL_2022.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(
                    null,
                    new JLabel("HAS PERDUT", new ImageIcon(imatge),
                            JLabel.LEFT),
                    "Ups!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        actualitzarText("Simulació acabada");
    }

    /**
     * Mètode per actualitzar el missatge informatiu inferior
     *
     * @param a missatge que es mostrarà
     */
    private void actualitzarText(String a) {
        texteMissatge.setText(a);
        contenedor.repaint();
    }
}
