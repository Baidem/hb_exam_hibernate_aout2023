package hb.exam;

import hb.exam.model.Utilisateur;
import hb.exam.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class HQL {
    public static void main(String[] args) {
        SessionFactory sf = new HibernateUtil().buildSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // Requête : le nom et le prénom de l’utilisateur qui a effectué le plus de commandes
        List<Object[]> req = session.createQuery(
                "SELECT c.utilisateur.nom, COUNT(d) " +
                        "FROM DetailsCommande d " +
                        "INNER JOIN d.commande c " +
                        "GROUP BY c.utilisateur " +
                        "ORDER BY COUNT(d) DESC"
        ).getResultList();
        System.out.println("------------------------------------");
        System.out.println("Le nom de l'utilisateur qui a fait le plus de commande est : " + req.get(0)[0]);
        System.out.println("------------------------------------");

        // Requête : le nombre de produits par catégories
        List<Object[]> req2 = session.createQuery(
                "SELECT c.nom, COUNT(p) " +
                        "FROM Categorie c " +
                        "JOIN c.produits p " +
                        "GROUP BY c.nom"
        ).getResultList();
        System.out.println("------------------------------------");
        System.out.println("Nombre de produits par catégories :");
        for (Object[] c: req2){
            System.out.println(c[0] + " " + c[1]);
        }
        System.out.println("------------------------------------");

        // Requête : le nombre de commentaire par utilisateurs.
        List<Object[]> req3 = session.createQuery(
                "SELECT u.nom, COUNT(c) " +
                        "FROM Utilisateur u " +
                        "LEFT JOIN u.commentaires c " +
                        "GROUP BY u.nom", Object[].class
        ).getResultList();
        System.out.println("------------------------------------");
        System.out.println("Le nombre de commentaire par utilisateurs");
        for (Object[] o: req3){
            System.out.println(o[0] + " " +o[1]);
        }
        System.out.println("------------------------------------");

        // Requête : le prix moyen des produits par catégories.
        List<Object[]> req4 = session.createQuery(
                "SELECT c.nom, AVG(p.prix) " +
                        "FROM Categorie c " +
                        "INNER JOIN c.produits p GROUP BY c.nom"
                , Object[].class
        ).getResultList();
        System.out.println("------------------------------------");
        System.out.println("La moyenne des prix par catégories.");
        for (Object[] o: req4){
            System.out.println(o[0] + " " +o[1]);
        }
        System.out.println("------------------------------------");

        // Requête : supprimer les utilisateurs n’ayant pas réalisé de commandes depuis plus de 2 ans.
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, -2);
        List<Utilisateur> req5 = session.createQuery(
                "SELECT u FROM Utilisateur u " +
                        "WHERE NOT EXISTS " +
                        "(SELECT c FROM Commande c " +
                        "WHERE c.utilisateur = u " +
                        "AND c.dateCommande > :plus2Ans)"
        ).setParameter("plus2Ans", gc).getResultList();
        System.out.println("------------------------------------");
        System.out.println("Utilisateurs n’ayant pas réalisé de commandes depuis plus de 2 ans");
        for (Utilisateur o: req5){
            System.out.println(o);
        }

//        session.createQuery(
//                "DELETE FROM DetailsCommande d WHERE EXISTS " +
//                        "(SELECT u FROM Utilisateur u WHERE NOT EXISTS " +
//                        "(SELECT c FROM Commande c WHERE c.utilisateur = u AND c.dateCommande > :plus2Ans))"
//        ).setParameter("plus2Ans", gc).executeUpdate();

        session.createQuery(
                "DELETE FROM Utilisateur u WHERE NOT EXISTS (SELECT c FROM Commande c WHERE c.utilisateur = u AND c.dateCommande > :plus2Ans)"
        ).setParameter("plus2Ans", gc).executeUpdate();
        System.out.println("------------------------------------");



//        // Requête 1 !!
//        Long nbUtilisateur =
//                session.createQuery("SELECT COUNT(u) FROM Utilisateur  u", Long.class)
//                        .getSingleResult();
//
//        System.out.println("Il y a "+nbUtilisateur+" qui sont entrain de commander !");
//
//        // Requête 2
//        List<Long> nbCommandes =
//                session.createQuery("SELECT COUNT(u) FROM Commande c " +
//                        "INNER JOIN c.utilisateur u " +
//                        "GROUP BY u.id", Long.class ).getResultList();
//
//        System.out.println("Il y a actuellement "+nbCommandes.size()+"commandes en cours");
//
//        // Requête 3
//        List<Object[]> moyenneParCategorie =
//                (List<Object[]>) session.createQuery("SELECT c.libelle, AVG(p.prix) FROM Produit p " +
//                        "INNER JOIN p.categorie c GROUP BY c.id").getResultList();
//
//        for (Object[] categMoyenne: moyenneParCategorie){
//            System.out.println("Catégorie : " + categMoyenne[0]);
//            System.out.println("Moyenne des prix : " + categMoyenne[1]);
//            System.out.println("___");
//        }
//
//        // Requête 4
//        String baconResearch = "%bacon%";
//
//        List<Produit> produits = session.createQuery(
//                        "FROM Produit p WHERE p.description LIKE (:param)", Produit.class)
//                .setParameter("param", baconResearch).getResultList();
//
//        for (Produit produit: produits){
//            System.out.println(produit.getDescription());
//        }
//
//        // Requête 5
//
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.add(Calendar.YEAR, -18);
//
//
//        Double moyenneMineur = session.createQuery(
//                "SELECT AVG(p.prix) FROM Commande c " +
//                        "LEFT JOIN c.utilisateur u " +
//                        "LEFT JOIN c.produit p WHERE u.dateNaissance > :dateMineur"
//                , Double.class
//        ).setParameter("dateMineur", gc).getSingleResult();
//
//        System.out.println("Moyenne des commandes d'un mineur : " + moyenneMineur);
//
//
//        // Requête 6 :
//
//        Double moyenneMajeur = session.createQuery(
//                "SELECT AVG(p.prix) FROM Commande c " +
//                        "LEFT JOIN c.utilisateur u " +
//                        "LEFT JOIN c.produit p WHERE u.dateNaissance <= :dateMineur"
//                , Double.class
//        ).setParameter("dateMineur", gc).getSingleResult();
//
//        System.out.println("Moyenne des commandes d'un majeur : " + moyenneMajeur);
//
//
//        // Requête 7
//
//        Double moyen = session.createQuery(
//                "SELECT AVG(p.prix) FROM Commande c " +
//                        "LEFT JOIN c.utilisateur u " +
//                        "LEFT JOIN c.produit p"
//                , Double.class
//        ).getSingleResult();
//
//        System.out.println("Moyenne des commandes : " + moyen);


        tx.commit();


        sf.close();
    }
}
