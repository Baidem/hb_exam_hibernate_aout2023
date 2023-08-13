package hb.exam.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un nom de produit")
    private String nom;
    @Column
    @Type(type = "text")
    private String description;
    @Column(columnDefinition = "float(10,2)")
    private double prix;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id", referencedColumnName = "id")
    private Categorie categorie;
    public Categorie getCategorie(){
        return categorie;
    }
    public void setCategorie(Categorie categorie){
        this.categorie = categorie;
    }

    @OneToMany(mappedBy = "produit")
    private List<DetailsCommande> detailsCommandes;

    public Produit() {
        this.detailsCommandes = new ArrayList<>();
        this.commentaires = new ArrayList<>();
    }
    public void addDetailsCommande(DetailsCommande detailsCommande){
        this.detailsCommandes.add(detailsCommande);
    }
    public void removeDetailsCommande(DetailsCommande detailsCommande){
        this.detailsCommandes.remove(detailsCommande);
    }
    public List<DetailsCommande> getDetailsCommandes(){
        return this.detailsCommandes;
    }

    @OneToMany(mappedBy = "produit")
    private List<Commentaire> commentaires;

    public void addCommentaire(Commentaire commentaire){
        this.commentaires.add(commentaire);
    }
    public void removeCommentaire(Commentaire commentaire){
        this.commentaires.remove(commentaire);
    }
    public List<Commentaire> getCommentaires(){
        return this.commentaires;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setDetailsCommandes(List<DetailsCommande> detailsCommandes) {
        this.detailsCommandes = detailsCommandes;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id = " + id +
                ", nom = '" + nom + '\'' +
                ", description = '" + description + '\'' +
                ", prix = " + prix +
                ", categorie = " + categorie +
                '}';
    }
}
