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
        Scanner scanner = new Scanner(System.in);
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
        Commentaire superMushroom1 = new Commentaire();
        superMushroom1.setCommentaire("Le Super Mushroom m'a sauvé la vie plus d'une fois !" );
        superMushroom1.setUtilisateur(toad);
        superMushroom1.setProduit(superMushroom);
        Set<ConstraintViolation<Commentaire>> errorsCommentaire = validator.validate(superMushroom1);

        Commentaire superMushroom2 = new Commentaire();
        superMushroom2.setCommentaire("Je me sens super puissant quand je deviens plus grand avec le Super Mushroom.");
        superMushroom2.setUtilisateur(mario);
        superMushroom2.setProduit(superMushroom);
        errorsCommentaire.addAll(validator.validate(superMushroom2));

        Commentaire superMushroom3 = new Commentaire();
        superMushroom3.setCommentaire("C'est cool, mais je préfère quand même les fleurs de feu.");
        superMushroom3.setUtilisateur(luigi);
        superMushroom3.setProduit(superMushroom);
        errorsCommentaire.addAll(validator.validate(superMushroom3));

        Commentaire superMushroom4 = new Commentaire();
        superMushroom4.setCommentaire("J'aimerais bien essayer le Super Mushroom.");
        superMushroom4.setUtilisateur(koopa);
        superMushroom4.setProduit(superMushroom);
        errorsCommentaire.addAll(validator.validate(superMushroom4));

        Commentaire superMushroom5 = new Commentaire();
        superMushroom5.setCommentaire("Le Super Mushroom est un incontournable pour surmonter les obstacles difficiles !");
        superMushroom5.setUtilisateur(mario);
        superMushroom5.setProduit(superMushroom);
        errorsCommentaire.addAll(validator.validate(superMushroom5));

        Commentaire superMushroom6 = new Commentaire();
        superMushroom6.setCommentaire("J'ai grandi en utilisant le Super Mushroom. Ça me rappelle de bons souvenirs.");
        superMushroom6.setUtilisateur(luigi);
        superMushroom6.setProduit(superMushroom);
        errorsCommentaire.addAll(validator.validate(superMushroom6));

        Commentaire fireFlower1 = new Commentaire();
        fireFlower1.setCommentaire("Avec la Fire Flower, je me sens tellement puissant en lançant des boules de feu sur les ennemis. C'est génial pour nettoyer le chemin !");
        fireFlower1.setUtilisateur(mario);
        fireFlower1.setProduit(fireFlower);
        errorsCommentaire.addAll(validator.validate(fireFlower1));

        Commentaire fireFlower2 = new Commentaire();
        fireFlower2.setCommentaire("La Fire Flower est mon arme préférée pour affronter les ennemis. Qui a besoin d'épées quand on peut lancer des boules de feu ?");
        fireFlower2.setUtilisateur(koopa);
        fireFlower2.setProduit(fireFlower);
        errorsCommentaire.addAll(validator.validate(fireFlower2));

        Commentaire fireFlower3 = new Commentaire();
        fireFlower3.setCommentaire("Voir les ennemis brûler sous mes boules de feu est tellement satisfaisant.");
        fireFlower3.setUtilisateur(koopa);
        fireFlower3.setProduit(fireFlower);
        errorsCommentaire.addAll(validator.validate(fireFlower3));

        Commentaire fireFlower4 = new Commentaire();
        fireFlower4.setCommentaire("Les fleurs de feu sont mon moyen préféré pour mettre un peu de piquant dans le jeu.");
        fireFlower4.setUtilisateur(luigi);
        fireFlower4.setProduit(fireFlower);
        errorsCommentaire.addAll(validator.validate(fireFlower4));

        Commentaire superStar1 = new Commentaire();
        superStar1.setCommentaire("Rien ne peut me toucher quand je suis sous l'effet de la Super Star ! Woo-hoo !");
        superStar1.setUtilisateur(mario);
        superStar1.setProduit(superStar);
        errorsCommentaire.addAll(validator.validate(superStar1));

        Commentaire superStar2 = new Commentaire();
        superStar2.setCommentaire("Je saute partout quand je suis sous l'effet de la Super Star. C'est comme si j'étais sur un nuage.");
        superStar2.setUtilisateur(toad);
        superStar2.setProduit(superStar);
        errorsCommentaire.addAll(validator.validate(superStar2));

        Commentaire oneUpMushroom1 = new Commentaire();
        oneUpMushroom1.setCommentaire("Ces 1-Up Mushrooms sont comme des trésors cachés. Je ne les laisse jamais passer !");
        oneUpMushroom1.setUtilisateur(mario);
        oneUpMushroom1.setProduit(oneUpMushroom);
        errorsCommentaire.addAll(validator.validate(oneUpMushroom1));

        Commentaire oneUpMushroom2 = new Commentaire();
        oneUpMushroom2.setCommentaire("Ces 1-Up Mushrooms sont comme des trésors cachés. Je ne les laisse jamais passer !");
        oneUpMushroom2.setUtilisateur(mario);
        oneUpMushroom2.setProduit(oneUpMushroom);
        errorsCommentaire.addAll(validator.validate(oneUpMushroom2));

        Commentaire oneUpMushroom3 = new Commentaire();
        oneUpMushroom3.setCommentaire("Une vie supplémentaire, c'est toujours bon à prendre.");
        oneUpMushroom3.setUtilisateur(luigi);
        oneUpMushroom3.setProduit(oneUpMushroom);
        errorsCommentaire.addAll(validator.validate(oneUpMushroom3));

        Commentaire capeFeather1 = new Commentaire();
        capeFeather1.setCommentaire("Voler dans les airs avec la Cape Feather est l'un de mes passe-temps préférés.");
        capeFeather1.setUtilisateur(toad);
        capeFeather1.setProduit(capeFeather);
        errorsCommentaire.addAll(validator.validate(capeFeather1));

        Commentaire cfrogSuit1 = new Commentaire();
        cfrogSuit1.setCommentaire("Le Frog Suit est parfait pour les niveaux d'eau. Je me sens comme un vrai nageur.");
        cfrogSuit1.setUtilisateur(luigi);
        cfrogSuit1.setProduit(frogSuit);
        errorsCommentaire.addAll(validator.validate(cfrogSuit1));

        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(superMushroom1);
            session.persist(superMushroom2);
            session.persist(superMushroom3);
            session.persist(superMushroom4);
            session.persist(superMushroom5);
            session.persist(superMushroom6);
            session.persist(fireFlower1);
            session.persist(fireFlower2);
            session.persist(fireFlower3);
            session.persist(fireFlower4);
            session.persist(superStar1);
            session.persist(superStar2);
            session.persist(oneUpMushroom1);
            session.persist(oneUpMushroom2);
            session.persist(oneUpMushroom3);
            session.persist(capeFeather1);
            session.persist(cfrogSuit1);

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

        Commande commande5 = new Commande();
        commande5.setDateCommande(new GregorianCalendar(2015, Calendar.APRIL,22));
        commande5.setUtilisateur(koopa);
        errorsCommande.addAll(validator.validate(commande5));


        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(commande1);
            session.persist(commande2);
            session.persist(commande3);
            session.persist(commande4);
            session.persist(commande5);

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

        DetailsCommande detailsCommande5 = new DetailsCommande();
        detailsCommande5.setQuantite(1);
        detailsCommande5.setCommande(commande5);
        detailsCommande5.setProduit(capeFeather);
        errorsDetailsCommande.addAll(validator.validate(detailsCommande5));


        if(errorsCategorie.isEmpty()){
            session = sf.getCurrentSession();
            tx = session.beginTransaction();

            session.persist(detailsCommande1);
            session.persist(detailsCommande1_2);
            session.persist(detailsCommande2);
            session.persist(detailsCommande3);
            session.persist(detailsCommande4);
            session.persist(detailsCommande4_2);
            session.persist(detailsCommande5);

            tx.commit();
            System.out.println("Données des details_commande mis à jour.");
        } else {
            System.out.println("Les données des details_commande ne sont pas valident.");
            for (ConstraintViolation<DetailsCommande> error:errorsDetailsCommande){
                System.out.println(error.getMessage());
            }
        }

        // Enregistrer un utilisateur en utilisant la saisies console
        Utilisateur u = new Utilisateur();
        boolean isSave = false;
        do {
        u.setNom(saisieTexteConsole("Saisir le nom de l'utilisateur : ", scanner));
        u.setEmail(saisieTexteConsole("Saisir l'email de l'utilisateur : ", scanner));
        u.setMotDePasse(saisieTexteConsole("Saisir le mot de passe de l'utilisateur : ", scanner));
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

        scanner.close();
        sf.close();
    }

    public static String saisieTexteConsole(String libelle, Scanner scanner){

        System.out.println(libelle);
        String st = scanner.nextLine();

        return st;
    }
}