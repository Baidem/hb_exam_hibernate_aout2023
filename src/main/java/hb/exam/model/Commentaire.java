package hb.exam.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "commentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un commentaire")
    @Type(type = "text")
    private String commentaire;

    @ManyToOne(optional = true)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;
    public Utilisateur getUtilisateur(){
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;
    public Produit getProduit(){
        return produit;
    }
    public void setProduit(Produit produit){
        this.produit = produit;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
