package fr.fredo.gestioncontjdbc;

//
//CONTACT DAO
//
import java.util.*;
import utilitairesMG.jdbc.*;
import utilitairesMG.divers.*;
import java.sql.*;

public class ContactDAO {
 
 private AccesBase accesBase;
 
 private JeuResultat jeuResultat;
 
 public ContactDAO(AccesBase accesBase)
 {
     this.accesBase = accesBase;
 }
 
 public Vector<Contact> lireListe() throws SQLException
 {
     Vector<Contact> listeContacts;
     Contact contact;
     
     String select = "SELECT * FROM CONTACT";
     
     int nombreDeContacts;
     Vector ligneContact;
     int i;
     
     jeuResultat = accesBase.executeQuery(select);
     
     listeContacts = new Vector<Contact>();
     nombreDeContacts = (jeuResultat.getLignes()).size();
     
     for (i = 0; i < nombreDeContacts; i++)
     {
         ligneContact = (Vector) ((jeuResultat.getLignes()).elementAt(i));
         
         contact = new Contact();
         contact.setNumero((Integer) ligneContact.elementAt(0));
         contact.setNom((String) ligneContact.elementAt(1));
         contact.setAdresse((String) ligneContact.elementAt(2));
         contact.setCodePostal((String) ligneContact.elementAt(3));
         contact.setVille((String) ligneContact.elementAt(4));
         contact.setMail((String) ligneContact.elementAt(5));
         listeContacts.addElement(contact);
     }
     return listeContacts;
 }
 
 public Vector<Colonne> getListeColonnes()
 {
     return jeuResultat.getColonnes();
 }

}

