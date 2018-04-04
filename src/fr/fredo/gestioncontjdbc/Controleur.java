package fr.fredo.gestioncontjdbc;

//
//CONTROLEUR
//
import java.util.*;
import utilitairesMG.divers.*;
import utilitairesMG.graphique.*;
import utilitairesMG.jdbc.*;
import java.sql.*;

public class Controleur 
{
 
 private static Fenetre maFenetre;
 private static ContactDAO contactDAO;
 private static BaseDeDonnees base;
 private static AccesBase accesBase;
 
 public static void main(String args[])
 {
     try
     {
         Class.forName("com.mysql.jdbc.Driver");
     }
     catch (ClassNotFoundException e)
     {
         System.out.println("Driver inconnu : " + e.getMessage());
         System.exit(0);
     }
     
     base = new BaseDeDonnees("jdbc:mysql://localhost/gestioncontact","newuser","newuser");
     base.setFormatDate("dd/MM/yyyy");
     
     accesBase = new AccesBase(base);
     
     contactDAO = new ContactDAO(accesBase);
     
     javax.swing.SwingUtilities.invokeLater
     (
         new Runnable()
         {
             public void run()
             {
                 LF.setLF();
                 maFenetre = new Fenetre("GestionContactJdbc");
             }
         }
     );   
 }
 
 
 public static void demandeContacts()
 {
     Vector<Contact> listeContacts;
     Vector<Colonne> listeColonnes;
     
     try
     {
         accesBase.getConnection();
         
         try
         {
             listeContacts = contactDAO.lireListe();
             listeColonnes = contactDAO.getListeColonnes();
             
             maFenetre.afficheContacts(listeContacts, listeColonnes);
         }
         catch (SQLException e)
         {
             maFenetre.afficheMessage(e.getMessage());
         }
         finally
         {
             accesBase.closeConnection();
         }
     }
     catch (SQLException e)
     {
         maFenetre.afficheMessage(e.getMessage());
     }
 }
 
 /*public static void demandeVersements()
 {
     maFenetre.afficheMessage("Gestion des versements non réalisée");
 }
 
 public static void demandeSecteurs()
 {
     maFenetre.afficheMessage("Gestion des secteurs non réalisée");
 }*/
 
 public static void majContacts(Vector<Contact> contactsModifies,
                                Vector<Contact> contactsInseres,
                                Vector<Contact> contactsSupprimes)
 {
     maFenetre.valideItemContact();
     
     System.out.println("\nListe des contacts modifiés :\n");
     
     if (contactsModifies.size() == 0)
     {
         System.out.println("Il n'ya pas de contacts modifiés.");
     }
     else
     {
         for (int i = 0; i < contactsModifies.size(); i++)
         {
             System.out.println(contactsModifies.elementAt(i));
         }
     }
     
     System.out.println("\n\nListe de contacts inserés :\n");
     
     if (contactsInseres.size() == 0)
     {
         System.out.println("Il n'y a pas de contacts inserés.");
     }
     else
     {
         for (int i = 0; i < contactsInseres.size(); i++)
         {
             System.out.println(contactsInseres.elementAt(i));
         }
     }
     
     System.out.println("\n\nListe de contacts supprimés :\n");
     
     if (contactsSupprimes.size() == 0)
     {
         System.out.println("Il n'y a pas de contacts supprimés.");
     }
     else
     {
         for (int i = 0; i < contactsSupprimes.size(); i++)
         {
             System.out.println(contactsSupprimes.elementAt(i));
         }
     }
 }
 
 public static void arreter()
 {
     System.exit(0);
 }
}
