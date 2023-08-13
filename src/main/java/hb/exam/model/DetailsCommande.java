package hb.exam.model;

import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "details_commande")
public class DetailsCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotNull(message = "Veuillez saisir une quantité.")
    private int quantite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;
    public Produit getProduit(){
        return produit;
    }
    public void setProduit(Produit produit){
        this.produit = produit;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "commande_id", referencedColumnName = "id")
    private Commande commande;
    public Commande getCommande(){
        return commande;
    }
    public void setCommande(Commande commande){
        this.commande = commande;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public long getId() {
        return id;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return "DetailsCommande{" +
                "id = " + id +
                ", quantite = " + quantite +
                ", produit = " + produit.getNom() +
                ", commande id = " + commande.getId() +
                '}';
    }
}
