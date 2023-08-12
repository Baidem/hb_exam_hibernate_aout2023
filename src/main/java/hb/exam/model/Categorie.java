package hb.exam.model;

import jakarta.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un nom de catégorie.")
    private String nom;
    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

    public Categorie() {
        this.produits = new ArrayList<>();
    }
    public void addProduit(Produit produit){
        this.produits.add(produit);
    }
    public void removeProduit(Produit produit){
        this.produits.remove(produit);
    }
    public List<Produit> getProduits(){
        return this.produits;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
