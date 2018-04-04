package fr.fredo.gestioncontjdbc;

/**
//CLASSE FENETRE INTERNE
*/
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.table.*;

public class FenetreInterneContact extends JInternalFrame implements ActionListener
{
private JMenuBar barreMenu;
private JMenu menuEdition;
private JMenuItem supprimerLignesItem;
private JMenuItem restaurerLignesItem;

private JPanel panneauFond;

private JTable table;
private ModeleTableContact modeleTable;
private ModeleColonneTable modeleColonne;

private JScrollPane defileur;

//
//CONSTRUCTEUR
//
public FenetreInterneContact(Vector<Contact> listeContacts,
                         Vector<Colonne> listeColonnes)
{
    super("CONTACTS", true, true, true, true);
    //addInternalFrameListener(new EcouteInternalFrameClosing());

    //
    //CREATION DU JPanel
    //
panneauFond = new JPanel();
panneauFond.setLayout(new BorderLayout());
panneauFond.setPreferredSize(new Dimension(650, 150));

//
//CREATION DU JTable
//
table = new JTable();
table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

//
//RECHERCHE TAILLE DE POLICE DE LA LETTRE M
//
Font fontParDefaut = table.getFont();
int tailleM = table.getFontMetrics(fontParDefaut).stringWidth("M");

//
//CREATION MODELE DE TABLE ET DE COLONNE
//
modeleTable = new ModeleTableContact(listeContacts, listeColonnes);
modeleColonne = new ModeleColonneTable(listeColonnes, tailleM);

//
//CHANGEMENT DE L'EDITEUR DE LA COLONNE 5
//
JComboBox combo = new JComboBox();
combo.addItem(null);
for (int i = 1; i <= 4; i++)
{
    combo.addItem(i);
}

DefaultCellEditor editeur = new DefaultCellEditor(combo);
editeur.setClickCountToStart(2);

modeleColonne.setEditeurColonne(5, editeur);

//
//AJOUT DES MODELES A LA JTable
//
table.setModel(modeleTable);
table.setColumnModel(modeleColonne);
defileur = new JScrollPane(table);
defileur.getViewport().setBackground(new Color(220, 170, 255));

panneauFond.add(defileur);

getContentPane().add(panneauFond);

pack();
setVisible(true);
}

	@Override
public void actionPerformed(ActionEvent e) 
{
    if (e.getSource() == supprimerLignesItem)
    {
        int[] lignesSelectionnees = table.getSelectedRows();
        
        if (lignesSelectionnees.length > 0)
        {
            int n = JOptionPane.showConfirmDialog(this, "Confirmez vous la suppréssion des " +
                    "lignes selectionnées ?",
                    "Confirmation suppression",
                    JOptionPane.YES_NO_OPTION);
            if (n == 0)
            {
                modeleTable.supprimer(lignesSelectionnees);
                table.revalidate();
                if (modeleTable.getListeContactsSupprimes().size() > 0)
                {
                    restaurerLignesItem.setEnabled(true);
                }
            }
                
        }
    }
    else
    {
        modeleTable.restaurer();
        table.revalidate();
        restaurerLignesItem.setEnabled(false);
    }
}

private void afficheMessage(String message)
{
    JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
}

/*private class EcouteInternalFrameClosing extends InternalFrameAdapter
{
    public void InternalFrameClosing(InternalFrameEvent e)
    {
        Controleur.majContacts(modeleTable.getListeContacts('M'),
                               modeleTable.getListeContacts('I'),
                               modeleTable.getListeContactsSupprimes());
    }
}*/  
}

