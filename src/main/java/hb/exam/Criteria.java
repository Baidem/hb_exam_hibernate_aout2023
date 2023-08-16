package hb.exam;

import hb.exam.model.*;
import hb.exam.utils.HibernateUtil;
import hb.exam.utils.SysOut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Criteria {
    public static void main(String[] args) {
        SessionFactory sf = new HibernateUtil().buildSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();
        String title;

        // Criteria
        CriteriaBuilder qb = session.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = qb.createQuery(Utilisateur.class);

        // Requête : Afficher les produits en vente du plus chère au moins chère.
        CriteriaQuery<Produit> cqProduit1 = qb.createQuery(Produit.class);
        Root<Produit> rootProduit1 = cqProduit1.from(Produit.class);
        cqProduit1.select(rootProduit1).orderBy(qb.desc(rootProduit1.get("prix")));
        List<Produit> resultatCriteria1 = session.createQuery(cqProduit1).getResultList();

        title = "Produits en vente du plus chère au moins chère.";
        SysOut.printTitle(title);

        for (Produit p : resultatCriteria1){
            System.out.println(p.getPrix() + " pièces / " + p.getNom());
        }
        SysOut.printLine(title.length());

        // Requête : Afficher les produits en vente du moins chère au plus chère.
        CriteriaQuery<Produit> cqProduit2 = qb.createQuery(Produit.class);
        Root<Produit> rootProduit2 = cqProduit2.from(Produit.class);
        cqProduit2.select(rootProduit2).orderBy(qb.asc(rootProduit2.get("prix")));
        List<Produit> resultatCriteria2 = session.createQuery(cqProduit2).getResultList();

        title = "Produits en vente du moins chère au plus chère.";
        SysOut.printTitle(title);

        for (Produit p : resultatCriteria2){
            System.out.println(p.getPrix() + " pièces / " + p.getNom());
        }
        SysOut.printLine(title.length());

        // Requête : Afficher les produits achetés en plus grande quantité.
        CriteriaQuery<Tuple> tupleNomProduitEtQuantite = qb.createTupleQuery();
        Root<Produit> rootProduitMeilleurVente = tupleNomProduitEtQuantite.from(Produit.class);
        Join<Produit, DetailsCommande> join = rootProduitMeilleurVente.join("detailsCommandes", JoinType.INNER);

        tupleNomProduitEtQuantite.multiselect(
                rootProduitMeilleurVente.get("nom"),
                qb.sum(join.get("quantite"))
        );

        tupleNomProduitEtQuantite.groupBy(rootProduitMeilleurVente.get("nom")).orderBy(qb.desc(join.get("quantite")));

        List<Tuple> resultatCriteria3 = session.createQuery(tupleNomProduitEtQuantite).setMaxResults(3).getResultList();

        title = "Liste des produits achetés en plus grande quantité. (Les 3 meilleurs ventes)";
        SysOut.printTitle(title);

        for (Tuple tuple : resultatCriteria3) {
            System.out.println(tuple.get(0) + " " + tuple.get(1));
        }
        SysOut.printLine(title.length());

        // Requête : Afficher la commande effectuée la plus chère.
        CriteriaQuery<Tuple> tupleCommandeUtilisateurPrix = qb.createTupleQuery();
        Root<Commande> rootCommandeChere = tupleCommandeUtilisateurPrix.from(Commande.class);
        Join<Commande, DetailsCommande> joinDetailsCommande = rootCommandeChere.join("detailsCommandes", JoinType.INNER);
        Join<DetailsCommande, Produit> joinProduit = joinDetailsCommande.join("produit", JoinType.INNER);
        Join<Commande, Utilisateur> joinUtilisateurs = rootCommandeChere.join("utilisateur", JoinType.INNER);

        Expression<Double> montantTotal = qb.prod(joinDetailsCommande.get("quantite"), joinProduit.get("prix"));

        tupleCommandeUtilisateurPrix.multiselect(
                rootCommandeChere.get("id"),
                joinUtilisateurs.get("nom"),
                rootCommandeChere.get("dateCommande"),
                montantTotal.alias("montantTotal")
        );

        tupleCommandeUtilisateurPrix.orderBy(qb.desc(montantTotal));

        List<Tuple> resultatCriteria4 = session.createQuery(tupleCommandeUtilisateurPrix).setMaxResults(1).getResultList();

        title = "La commande la plus chère.";
        SysOut.printTitle(title);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Tuple tuple : resultatCriteria4) {
            System.out.println("Commande ID : " + tuple.get(0));

            GregorianCalendar gregorianCalendar = (GregorianCalendar) tuple.get(2);
            Date dateCommande = gregorianCalendar.getTime();
            System.out.println("Date de commande : " + dateFormat.format(dateCommande));

            System.out.println("Nom d'utilisateur : " + tuple.get(1));

            System.out.println("Montant total : " + tuple.get("montantTotal") + " pièces");
        }
        SysOut.printLine(title.length());

        // Requête : Afficher le produit ayant le plus de commentaires.
        CriteriaQuery<Tuple> tupleNomProduitNbCommentaire = qb.createTupleQuery();
        Root<Produit> rootProduit = tupleNomProduitNbCommentaire.from(Produit.class);
        Join<Produit, Commentaire> joinProduitCommentaire = rootProduit.join("commentaires", JoinType.INNER);

        tupleNomProduitNbCommentaire.multiselect(
                rootProduit.get("nom"),
                qb.count(joinProduitCommentaire)
        );

        tupleNomProduitNbCommentaire.groupBy(rootProduit.get("id"));

        tupleNomProduitNbCommentaire.orderBy(qb.desc(qb.count(joinProduitCommentaire)));

        List<Tuple> resultatCriteria5 = session.createQuery(tupleNomProduitNbCommentaire)
                .setMaxResults(1)
                .getResultList();

        title = "Le produit ayant le plus de commentaires.";
        SysOut.printTitle(title);

        for (Tuple tuple : resultatCriteria5) {
            System.out.println("Nom du produit : " + tuple.get(0));
            System.out.println("Nombre de commentaires : " + tuple.get(1));
        }
        SysOut.printLine(title.length());

        tx.commit();
        sf.close();
    }
}
