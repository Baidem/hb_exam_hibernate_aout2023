package hb.exam;

import hb.exam.model.Commande;
import hb.exam.model.Commentaire;
import hb.exam.model.DetailsCommande;
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

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Le nom de l'utilisateur qui a fait le plus de commande est : " + req.get(0)[0]);
        System.out.println("--------------------------------------------------------------------------");

        // Requête : le nombre de produits par catégories
        List<Object[]> req2 = session.createQuery(
                "SELECT c.nom, COUNT(p) " +
                        "FROM Categorie c " +
                        "JOIN c.produits p " +
                        "GROUP BY c.nom"
        ).getResultList();

        System.out.println("-----------------------------------");
        System.out.println("Nombre de produits par catégories :");
        System.out.println("-----------------------------------");
        for (Object[] c: req2){
            System.out.println(c[0] + " " + c[1]);
        }
        System.out.println("-----------------------------------");

        // Requête : le nombre de commentaire par utilisateurs.
        List<Object[]> req3 = session.createQuery(
                "SELECT u.nom, COUNT(c) " +
                        "FROM Utilisateur u " +
                        "LEFT JOIN u.commentaires c " +
                        "GROUP BY u.nom", Object[].class
        ).getResultList();

        System.out.println("-----------------------------------------");
        System.out.println("Le nombre de commentaire par utilisateurs");
        System.out.println("-----------------------------------------");
        for (Object[] o: req3){
            System.out.println(o[0] + " " +o[1]);
        }
        System.out.println("-----------------------------------------");

        // Requête : le prix moyen des produits par catégories.
        List<Object[]> req4 = session.createQuery(
                "SELECT c.nom, AVG(p.prix) " +
                        "FROM Categorie c " +
                        "INNER JOIN c.produits p GROUP BY c.nom"
                , Object[].class
        ).getResultList();

        System.out.println("-----------------------------------");
        System.out.println("La moyenne des prix par catégories.");
        System.out.println("-----------------------------------");
        for (Object[] o: req4){
            System.out.println(o[0] + " " +o[1]);
        }
        System.out.println("-----------------------------------");

        // Requête : supprimer les utilisateurs n’ayant pas réalisé de commandes depuis plus de 2 ans.
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, -2);
        List<Utilisateur> utilisateursASupprimer = session.createQuery(
                "FROM Utilisateur u " +
                        "WHERE NOT EXISTS " +
                        "(SELECT c FROM Commande c " +
                        "WHERE c.utilisateur = u " +
                        "AND c.dateCommande > :dateNowMoins2Ans)"
        ).setParameter("dateNowMoins2Ans", gc).getResultList();

        System.out.println("------------------------------------");
        System.out.println("Liste d'utilisateurs à supprimer." + utilisateursASupprimer.size());
        System.out.println("------------------------------------");
        for (Utilisateur u: utilisateursASupprimer){
            System.out.println(u);
        }
        System.out.println("------------------------------------");

        List<Commande> commandesASupprimer = session.createQuery(
                "FROM Commande c WHERE c.utilisateur IN :utilisateurs"
        ).setParameterList("utilisateurs", utilisateursASupprimer).getResultList();

        System.out.println("------------------------------------");
        System.out.println("Liste des commandes à supprimer " + commandesASupprimer.size());
        System.out.println("------------------------------------");
        for (Commande c: commandesASupprimer){
            System.out.println(c);
        }
        System.out.println("------------------------------------");

        List<DetailsCommande> detailsCommandesASupprimer = session.createQuery(
                "FROM DetailsCommande d WHERE d.commande IN :commandes"
        ).setParameterList("commandes", commandesASupprimer).getResultList();
        System.out.println("--------------------------------------------");
        System.out.println("Liste des détails commandes à supprimer " + detailsCommandesASupprimer.size());
        System.out.println("--------------------------------------------");
        for (DetailsCommande d : detailsCommandesASupprimer){
            System.out.println(d);
        }
        System.out.println("--------------------------------------------");

        List<Commentaire> commentairesASupprimer = session.createQuery(
                "FROM Commentaire c WHERE c.utilisateur IN :utilisateurs"
        ).setParameterList("utilisateurs", utilisateursASupprimer).getResultList();
        System.out.println("---------------------------------------");
        System.out.println("Liste des commentaires à supprimer " + commentairesASupprimer.size());
        System.out.println("---------------------------------------");
        for (Commentaire c: commentairesASupprimer){
            System.out.println(c);
        }
        System.out.println("---------------------------------------");

        if (!detailsCommandesASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM DetailsCommande d WHERE d IN :details")
                    .setParameterList("details", detailsCommandesASupprimer)
                    .executeUpdate();
            System.out.println("--------------------------------------------");
            System.out.println("Nombre de détails commandes supprimés : " + deletedCount);
            System.out.println("--------------------------------------------");
        }

        if (!commentairesASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM Commentaire c WHERE c IN :commentaires")
                    .setParameterList("commentaires", commentairesASupprimer)
                    .executeUpdate();
            System.out.println("----------------------------------------");
            System.out.println("Nombre de commmentaires supprimés : " + deletedCount);
            System.out.println("----------------------------------------");
        }

        if (!commandesASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM Commande c WHERE c IN :commandes")
                    .setParameterList("commandes", commandesASupprimer)
                    .executeUpdate();
            System.out.println("------------------------------------");
            System.out.println("Nombre de commandes supprimés : " + deletedCount);
            System.out.println("------------------------------------");
        }

        if (!utilisateursASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM Utilisateur u WHERE u IN :utilisateurs")
                    .setParameterList("utilisateurs", utilisateursASupprimer)
                    .executeUpdate();
            System.out.println("---------------------------------------");
            System.out.println("Nombre de utilisateurs supprimés : " + deletedCount);
            System.out.println("---------------------------------------");
        }

        tx.commit();
        sf.close();
    }
}
