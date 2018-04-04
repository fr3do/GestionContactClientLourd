package fr.fredo.gestioncontjdbc;

//
//FENETRE
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.fenetreinterne.*;

public class Fenetre extends JFrame implements ActionListener
{
private JMenuBar barreMenu;
private JMenu menuTables;
private JMenuItem itemContact;
private JMenuItem itemVersement;
private JMenuItem itemSecteur;

private JDesktopPaneMG panneauFenetres;
private JScrollPaneMG defilPanneauFenetres;

private FenetreInterneContact fenetreContact;

public Fenetre(String s)
{
    super(s);
    addWindowListener(new EcouteWindowClosing());


    itemContact = new JMenuItem("Contact");
    itemContact.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.ALT_MASK));

    //itemVersement = new JMenuItem("Vesement");
    //itemVersement.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.ALT_MASK));

    //itemSecteur = new JMenuItem("Secteur");
    //itemSecteur.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.ALT_MASK));

    itemContact.addActionListener(this);
    //itemVersement.addActionListener(this);
    //itemSecteur.addActionListener(this);

    menuTables = new JMenu("Tables");
    menuTables.add(itemContact);
    //menuTables.add(itemVersement);
    //menuTables.add(itemSecteur);

    barreMenu = new JMenuBar();
    barreMenu.add(menuTables);

    setJMenuBar(barreMenu);

    panneauFenetres = new JDesktopPaneMG();
    panneauFenetres.setBackground(new Color(170, 170, 255));
    panneauFenetres.setPreferredSize(new Dimension(900, 250));


    panneauFenetres.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    defilPanneauFenetres = new JScrollPaneMG(panneauFenetres);

    getContentPane().add(defilPanneauFenetres);

    pack();
    setVisible(true);   
}

public void actionPerformed(ActionEvent e)
{
    if (e.getSource() == itemContact)
    //{
        Controleur.demandeContacts();
    //}
    /*else
    {
        if (e.getSource() == itemVersement)
        {
            Controleur.demandeVersements();
        }
        else
        {
            Controleur.demandeSecteurs();
        }
    }*/
}

public void valideItemContact()
{
    itemContact.setEnabled(true);
}

public void afficheMessage(String message)
{
    JOptionPane.showMessageDialog(this, message, "information", JOptionPane.INFORMATION_MESSAGE);
}

public void afficheContacts(Vector<Contact> listeContacts,
                            Vector<Colonne> listeColonnes)
{
    itemContact.setEnabled(false);

    fenetreContact = new FenetreInterneContact(listeContacts, listeColonnes);

    panneauFenetres.add(fenetreContact);
    panneauFenetres.revalidate();
    panneauFenetres.repaint();

    try
    {
        fenetreContact.setSelected(true);
    }
    catch (PropertyVetoException ex)
    {
    }
}

private class EcouteWindowClosing extends WindowAdapter
{
    public void windowClosing(WindowEvent e)
    {
        Controleur.arreter();
    }
}
}

