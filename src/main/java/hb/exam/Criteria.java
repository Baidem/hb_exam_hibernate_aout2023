package hb.exam;

import hb.exam.model.Commande;
import hb.exam.model.Produit;
import hb.exam.model.Utilisateur;
import hb.exam.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Criteria {
    public static void main(String[] args) {
        SessionFactory sf = new HibernateUtil().buildSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // Criteria
        CriteriaBuilder qb = session.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = qb.createQuery(Utilisateur.class);

        // Afficher la liste des utilisateurs
        Root<Utilisateur> root = cq.from(Utilisateur.class);
        cq.select(root);
        List<Utilisateur> utilisateurs = session.createQuery(cq).getResultList();

        System.out.println("Liste de utilisateurs");
        for (Utilisateur u : utilisateurs){
            System.out.println(u);
        }

        // Afficher les produits en vente du plus chère au moins chère.
        CriteriaQuery<Produit> cqProduit1 = qb.createQuery(Produit.class);
        Root<Produit> rootProduit1 = cqProduit1.from(Produit.class);
        cqProduit1.select(rootProduit1).orderBy(qb.desc(rootProduit1.get("prix")));
        List<Produit> produitsParPrixDecs = session.createQuery(cqProduit1).getResultList();

        System.out.println("Produits classés par prix décroisant");
        for (Produit p : produitsParPrixDecs){
            System.out.println(p.getNom() + " " + p.getPrix() + " pièces jaunes");
        }

        // Afficher les produits en vente du moins chère au plus chère.
        CriteriaQuery<Produit> cqProduit2 = qb.createQuery(Produit.class);
        Root<Produit> rootProduit2 = cqProduit2.from(Produit.class);
        cqProduit2.select(rootProduit2).orderBy(qb.asc(rootProduit2.get("prix")));
        List<Produit> produitsParPrixAsc = session.createQuery(cqProduit2).getResultList();

        System.out.println("Produits classés par prix croisant");
        for (Produit p : produitsParPrixAsc){
            System.out.println(p.getNom() + " " + p.getPrix() + " pièces jaunes");
        }

        // afficher les produits acheté un plus grande quantité.
        CriteriaQuery<Produit> cqProduitMoinsCher = qb.createQuery(Produit.class);
        Root<Produit> rootProduitMoinsCher = cqProduitMoinsCher.from(Produit.class);
        cqProduitMoinsCher.select(rootProduitMoinsCher).orderBy(qb.asc(rootProduitMoinsCher.get("prix")));
        List<Produit> produitsMoinsChers = session.createQuery(cqProduitMoinsCher).getResultList();

        // afficher la commande effectuée la chère.
        // afficher le produit ayant le plus de commentaires.

        tx.commit();
        session.close();
        sf.close();

    }
}
