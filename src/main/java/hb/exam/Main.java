package hb.exam;

import hb.exam.model.*;
import hb.exam.utils.HibernateUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SessionFactory sf = new HibernateUtil().buildSessionFactory();
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();


        // Suppression des données existantes
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.createQuery("DELETE Commentaire ").executeUpdate();
        session.createQuery("DELETE DetailsCommande ").executeUpdate();
        session.createQuery("DELETE Produit ").executeUpdate();
        session.createQuery("DELETE Commande ").executeUpdate();
        session.createQuery("DELETE Utilisateur ").executeUpdate();
        session.createQuery("DELETE Categorie ").executeUpdate();

        tx.commit();

        // Ajout données utilisateurs
        Utilisateur mario = new Utilisateur();
        mario.setNom("Mario Mario");
        mario.setEmail("mario.mario@nintendo.com");
        mario.setMotDePasse("12BA5678");
        Set<ConstraintViolation<Utilisateur>> errorsUtilisateur = validator.validate(mario);

        Utilisateur luigi = new Utilisateur();
        luigi.setNom("Luigi Mario");
        luigi.setEmail("luigi.mario@nintendo.com");
        luigi.setMotDePasse("12BA5678");
        errorsUtilisateur.addAll(validator.validate(luigi));

        Utilisateur toad = new Utilisateur();
        toad.setNom("Toad Capitaine");
        toad.setEmail("toad.captain@nintendo.com");
        toad.setMotDePasse("12BA5678");
        errorsUtilisateur.addAll(validator.validate(toad));

        Utilisateur koopa = new Utilisateur();
        koopa.setNom("Koopa Troopa");
        koopa.setEmail("koopa.troopa@nintendo.com");
        koopa.setMotDePasse("12BA5678");
        errorsUtilisateur.addAll(validator.validate(koopa));

        if(errorsUtilisateur.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(mario);
            session.persist(luigi);
            session.persist(toad);
            session.persist(koopa);

            tx.commit();
            System.out.println("Données utilisateurs mis à jour.");
        } else {
            System.out.println("Les données des utilisateurs ne sont pas valident.");
            for (ConstraintViolation<Utilisateur> error:errorsUtilisateur){
                System.out.println(error.getMessage());
            }
        }

        // Ajouter données catégories
        Categorie powerup = new Categorie();
        powerup.setNom("Power Up");
        Set<ConstraintViolation<Categorie>> errorsCategorie = validator.validate(powerup);

        Categorie costume = new Categorie();
        costume.setNom("Costume");
        errorsCategorie.addAll(validator.validate(costume));

        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(powerup);
            session.persist(costume);

            tx.commit();
            System.out.println("Données des categories mis à jour.");
        } else {
            System.out.println("Les données des categories ne sont pas valident.");
            for (ConstraintViolation<Categorie> error:errorsCategorie){
                System.out.println(error.getMessage());
            }
        }

        // Ajouter données produits
        Produit superMushroom = new Produit();
        superMushroom.setNom("Super Mushroom");
        superMushroom.setDescription("Permet de grandir et de résister à un coup ennemi supplémentaire.");
        superMushroom.setPrix(10.00);
        superMushroom.setCategorie(powerup);
        Set<ConstraintViolation<Produit>> errorsProduit = validator.validate(superMushroom);

        Produit fireFlower = new Produit();
        fireFlower.setNom("Fire Flower");
        fireFlower.setDescription("Permet de lancer des boules de feu pour attaquer les ennemis.");
        fireFlower.setPrix(15.00);
        fireFlower.setCategorie(costume);
        errorsProduit.addAll(validator.validate(fireFlower));

        Produit superStar = new Produit();
        superStar.setNom("Super Star");
        superStar.setDescription("Rend invincible pendant une courte période.");
        superStar.setPrix(20.00);
        superStar.setCategorie(powerup);
        errorsProduit.addAll(validator.validate(superStar));

        Produit oneUpMushroom = new Produit();
        oneUpMushroom.setNom("1-Up Mushroom");
        oneUpMushroom.setDescription("Accorde une vie supplémentaire.");
        oneUpMushroom.setPrix(30.00);
        oneUpMushroom.setCategorie(powerup);
        errorsProduit.addAll(validator.validate(oneUpMushroom));

        Produit superLeaf = new Produit();
        superLeaf.setNom("Super Leaf");
        superLeaf.setDescription("Transforme en Tanooki, permet de voler brièvement en agitant sa queue.");
        superLeaf.setPrix(25.00);
        superLeaf.setCategorie(powerup);
        errorsProduit.addAll(validator.validate(superLeaf));

        Produit capeFeather = new Produit();
        capeFeather.setNom("Cape Feather");
        capeFeather.setDescription("La cape permet de voler et d'effectuer des attaques aériennes puissantes.");
        capeFeather.setPrix(35.00);
        capeFeather.setCategorie(powerup);
        errorsProduit.addAll(validator.validate(capeFeather));

        Produit superBell = new Produit();
        superBell.setNom("Super Bell");
        superBell.setDescription("Transforme en Cat, permet de grimper sur les murs et d'effectuer des attaques griffes.");
        superBell.setPrix(30.00);
        superBell.setCategorie(powerup);
        errorsProduit.addAll(validator.validate(superBell));

        Produit frogSuit = new Produit();
        frogSuit.setNom("Frog Suit");
        frogSuit.setDescription("Transforme en Frog, permet de nager plus efficacement et de sauter plus haut hors de l'eau.");
        frogSuit.setPrix(40.00);
        frogSuit.setCategorie(costume);
        errorsProduit.addAll(validator.validate(frogSuit));

        Produit hammerSuit = new Produit();
        hammerSuit.setNom("Hammer Suit");
        hammerSuit.setDescription("Transforme en Hammer, permet de lancer des marteaux sur les ennemis.");
        hammerSuit.setPrix(45.00);
        hammerSuit.setCategorie(costume);
        errorsProduit.addAll(validator.validate(hammerSuit));

        Produit penguinSuit = new Produit();
        penguinSuit.setNom("Penguin Suit");
        penguinSuit.setDescription("Transforme en Penguin, permet de glisser sur la glace et de lancer des boules de neige.");
        penguinSuit.setPrix(40.00);
        penguinSuit.setCategorie(costume);
        errorsProduit.addAll(validator.validate(penguinSuit));

        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(superMushroom);
            session.persist(fireFlower);
            session.persist(superStar);
            session.persist(oneUpMushroom);
            session.persist(superLeaf);
            session.persist(capeFeather);
            session.persist(superBell);
            session.persist(frogSuit);
            session.persist(hammerSuit);
            session.persist(penguinSuit);

            tx.commit();
            System.out.println("Données des produits mis à jour.");
        } else {
            System.out.println("Les données des produits ne sont pas valident.");
            for (ConstraintViolation<Produit> error:errorsProduit){
                System.out.println(error.getMessage());
            }
        }

        // Ajouter données commentaires
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setCommentaire("Super !");
        commentaire1.setUtilisateur(mario);
        commentaire1.setProduit(superMushroom);
        Set<ConstraintViolation<Commentaire>> errorsCommentaire = validator.validate(commentaire1);

        Commentaire commentaire2 = new Commentaire();
        commentaire2.setCommentaire("Let's go !");
        commentaire2.setUtilisateur(mario);
        commentaire2.setProduit(superMushroom);
        errorsCommentaire.addAll(validator.validate(commentaire2));

        Commentaire commentaire3 = new Commentaire();
        commentaire3.setCommentaire("Mario ?!");
        commentaire3.setUtilisateur(luigi);
        commentaire3.setProduit(fireFlower);
        errorsCommentaire.addAll(validator.validate(commentaire3));

        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(commentaire1);
            session.persist(commentaire2);
            session.persist(commentaire3);

            tx.commit();
            System.out.println("Données des commentaires mis à jour.");
        } else {
            System.out.println("Les données des commentaires ne sont pas valident.");
            for (ConstraintViolation<Commentaire> error:errorsCommentaire){
                System.out.println(error.getMessage());
            }
        }

        // Ajouter données commandes
        Commande commande1 = new Commande();
        commande1.setDateCommande(new GregorianCalendar(2021, Calendar.FEBRUARY,8));
        commande1.setUtilisateur(mario);
        Set<ConstraintViolation<Commande>> errorsCommande = validator.validate(commande1);

        Commande commande2 = new Commande();
        commande2.setDateCommande(new GregorianCalendar(2022, Calendar.NOVEMBER,20));
        commande2.setUtilisateur(luigi);
        errorsCommande.addAll(validator.validate(commande2));

        Commande commande3 = new Commande();
        commande3.setDateCommande(new GregorianCalendar(2023, Calendar.AUGUST,12));
        commande3.setUtilisateur(mario);
        errorsCommande.addAll(validator.validate(commande3));

        Commande commande4 = new Commande();
        commande4.setDateCommande(new GregorianCalendar(2015, Calendar.APRIL,2));
        commande4.setUtilisateur(koopa);
        errorsCommande.addAll(validator.validate(commande4));


        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(commande1);
            session.persist(commande2);
            session.persist(commande3);
            session.persist(commande4);

            tx.commit();
            System.out.println("Données des commandes mis à jour.");
        } else {
            System.out.println("Les données des commendes ne sont pas valident.");
            for (ConstraintViolation<Commande> error:errorsCommande){
                System.out.println(error.getMessage());
            }
        }

        // Ajouter données details_commande
        DetailsCommande detailsCommande1 = new DetailsCommande();
        detailsCommande1.setQuantite(10);
        detailsCommande1.setCommande(commande1);
        detailsCommande1.setProduit(superMushroom);
        Set<ConstraintViolation<DetailsCommande>> errorsDetailsCommande = validator.validate(detailsCommande1);

        DetailsCommande detailsCommande1_2 = new DetailsCommande();
        detailsCommande1_2.setQuantite(1);
        detailsCommande1_2.setCommande(commande1);
        detailsCommande1_2.setProduit(penguinSuit);
        errorsDetailsCommande.addAll(validator.validate(detailsCommande1_2));

        DetailsCommande detailsCommande2 = new DetailsCommande();
        detailsCommande2.setQuantite(8);
        detailsCommande2.setCommande(commande2);
        detailsCommande2.setProduit(fireFlower);
        errorsDetailsCommande.addAll(validator.validate(detailsCommande2));

        DetailsCommande detailsCommande3 = new DetailsCommande();
        detailsCommande3.setQuantite(1);
        detailsCommande3.setCommande(commande3);
        detailsCommande3.setProduit(fireFlower);
        errorsDetailsCommande.addAll(validator.validate(detailsCommande3));

        DetailsCommande detailsCommande4 = new DetailsCommande();
        detailsCommande4.setQuantite(5);
        detailsCommande4.setCommande(commande4);
        detailsCommande4.setProduit(hammerSuit);
        errorsDetailsCommande.addAll(validator.validate(detailsCommande4));

        DetailsCommande detailsCommande4_2 = new DetailsCommande();
        detailsCommande4_2.setQuantite(3);
        detailsCommande4_2.setCommande(commande4);
        detailsCommande4_2.setProduit(oneUpMushroom);
        errorsDetailsCommande.addAll(validator.validate(detailsCommande4_2));


        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(detailsCommande1);
            session.persist(detailsCommande1_2);
            session.persist(detailsCommande2);
            session.persist(detailsCommande3);
            session.persist(detailsCommande4);
            session.persist(detailsCommande4_2);

            tx.commit();
            System.out.println("Données des details_commande mis à jour.");
        } else {
            System.out.println("Les données des details_commande ne sont pas valident.");
            for (ConstraintViolation<DetailsCommande> error:errorsDetailsCommande){
                System.out.println(error.getMessage());
            }
        }

        // Enregistrer un utilisateur en utilisant la saisies console
        boolean isSave = false;
        Utilisateur u = new Utilisateur();
        do {
        u.setNom(saisieTexteConsole("Saisir le nom de l'utilisateur : "));
        u.setEmail(saisieTexteConsole("Saisir l'email de l'utilisateur : "));
        u.setMotDePasse(saisieTexteConsole("Saisir le mot de passe de l'utilisateur : "));
        Set<ConstraintViolation<Utilisateur>> errorsU = validator.validate(u);

        if(errorsU.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(u);
            session.persist(u);

            tx.commit();
            isSave = true;
            System.out.println("L'utilisateur a été enregistré.");
        } else {
            System.out.println("Les données des utilisateurs ne sont pas valident.");
            for (ConstraintViolation<Utilisateur> error:errorsU){
                System.out.println(error.getMessage());
            }
        }

        } while (!isSave);



        sf.close();
    }

    public static String saisieTexteConsole(String libelle){
        Scanner scanner = new Scanner(System.in);
        String st = new String();
        boolean is = false;
        do {
            try {
                System.out.println(libelle);
                 st = scanner.nextLine();
                is = false;
            } catch (Exception e){
                e.printStackTrace();
            }
        } while (is);
        return st;
    }
}