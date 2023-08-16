package hb.exam;

import hb.exam.model.Commande;
import hb.exam.model.Commentaire;
import hb.exam.model.DetailsCommande;
import hb.exam.model.Utilisateur;
import hb.exam.utils.HibernateUtil;
import hb.exam.utils.SysOut;
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
        String title;

        // Requête : le nom et le prénom de l’utilisateur qui a effectué le plus de commandes
        List<Object[]> req = session.createQuery(
                "SELECT c.utilisateur.nom, COUNT(d) " +
                        "FROM DetailsCommande d " +
                        "INNER JOIN d.commande c " +
                        "GROUP BY c.utilisateur " +
                        "ORDER BY COUNT(d) DESC"
        ).getResultList();

        title = "Le nom de l'utilisateur qui a fait le plus de commande est : " + req.get(0)[0];
        SysOut.printTitle(title);

        // Requête : le nombre de produits par catégories
        List<Object[]> req2 = session.createQuery(
                "SELECT c.nom, COUNT(p) " +
                        "FROM Categorie c " +
                        "JOIN c.produits p " +
                        "GROUP BY c.nom"
        ).getResultList();

        title = "Nombre de produits par catégories :";
        SysOut.printTitle(title);

        for (Object[] c: req2){
            System.out.println(c[0] + " " + c[1]);
        }
        SysOut.printLine(title.length());

        // Requête : le nombre de commentaire par utilisateurs.
                List<Object[]> req3 = session.createQuery(
                "SELECT u.nom, COUNT(c) " +
                        "FROM Utilisateur u " +
                        "LEFT JOIN u.commentaires c " +
                        "GROUP BY u.nom", Object[].class
        ).getResultList();

        title = "Le nombre de commentaire par utilisateurs";
        SysOut.printTitle(title);

        for (Object[] o: req3){
            System.out.println(o[0] + " " +o[1]);
        }
        SysOut.printLine(title.length());

        // Requête : le prix moyen des produits par catégories.
        List<Object[]> req4 = session.createQuery(
                "SELECT c.nom, AVG(p.prix) " +
                        "FROM Categorie c " +
                        "INNER JOIN c.produits p GROUP BY c.nom"
                , Object[].class
        ).getResultList();

        title = "La moyenne des prix par catégories.";
        SysOut.printTitle(title);

        for (Object[] o: req4){
            System.out.println(o[0] + " " +o[1]);
        }
        SysOut.printLine(title.length());

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

        title = "Liste d'utilisateurs à supprimer " + utilisateursASupprimer.size();
        SysOut.printTitle(title);

        for (Utilisateur u: utilisateursASupprimer){
            System.out.println(u);
        }
        SysOut.printLine(title.length());

        List<Commande> commandesASupprimer = session.createQuery(
                "FROM Commande c WHERE c.utilisateur IN :utilisateurs"
        ).setParameterList("utilisateurs", utilisateursASupprimer).getResultList();

        title = "Liste des commandes à supprimer " + commandesASupprimer.size();
        SysOut.printTitle(title);

        for (Commande c: commandesASupprimer){
            System.out.println(c);
        }
        SysOut.printLine(title.length());

        List<DetailsCommande> detailsCommandesASupprimer = session.createQuery(
                "FROM DetailsCommande d WHERE d.commande IN :commandes"
        ).setParameterList("commandes", commandesASupprimer).getResultList();

        title = "Liste des détails commandes à supprimer " + detailsCommandesASupprimer.size();
        SysOut.printTitle(title);

        for (DetailsCommande d : detailsCommandesASupprimer){
            System.out.println(d);
        }
        SysOut.printLine(title.length());

        List<Commentaire> commentairesASupprimer = session.createQuery(
                "FROM Commentaire c WHERE c.utilisateur IN :utilisateurs"
        ).setParameterList("utilisateurs", utilisateursASupprimer).getResultList();

        title = "Liste des commentaires à supprimer " + commentairesASupprimer.size();
        SysOut.printTitle(title);

        for (Commentaire c: commentairesASupprimer){
            System.out.println(c);
        }
        SysOut.printLine(title.length());

        if (!detailsCommandesASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM DetailsCommande d WHERE d IN :details")
                    .setParameterList("details", detailsCommandesASupprimer)
                    .executeUpdate();

            SysOut.printTitle("Nombre de détails commandes supprimés : " + deletedCount);
        }

        if (!commentairesASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM Commentaire c WHERE c IN :commentaires")
                    .setParameterList("commentaires", commentairesASupprimer)
                    .executeUpdate();

            SysOut.printTitle("Nombre de commmentaires supprimés : " + deletedCount);
        }

        if (!commandesASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM Commande c WHERE c IN :commandes")
                    .setParameterList("commandes", commandesASupprimer)
                    .executeUpdate();

            SysOut.printTitle("Nombre de commandes supprimés : " + deletedCount);
        }

        if (!utilisateursASupprimer.isEmpty()) {
            int deletedCount = session.createQuery("DELETE FROM Utilisateur u WHERE u IN :utilisateurs")
                    .setParameterList("utilisateurs", utilisateursASupprimer)
                    .executeUpdate();

            SysOut.printTitle("Nombre de utilisateurs supprimés : " + deletedCount);
        }

        tx.commit();
        sf.close();
    }
}
