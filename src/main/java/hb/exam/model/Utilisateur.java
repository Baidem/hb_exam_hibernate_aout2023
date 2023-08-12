package hb.exam.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un nom")
    private String nom;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Veuillez saisir un email")
    @Email(message = "L'email n'est pas valide")
    private String email;
    @Column(name = "mot_de_passe", nullable = false)
    @NotBlank(message = "Veuillez saisir un mot de passe")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Le mot de passe doit avoir 8 caractères, au moins une lettre et un chiffre")
    private String motDePasse;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commandes;

    public Utilisateur() {
        this.commandes = new ArrayList<>();
        this.commentaires = new ArrayList<>();
    }
    public void addCommande(Commande commande){
        this.commandes.add(commande);
    }
    public void removeCommande(Commande commande){
        this.commandes.remove(commande);
    }
    public List<Commande> getCommandes(){
        return this.commandes;
    }

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

    public void addCommentaire(Commentaire commentaire){
        this.commentaires.add(commentaire);
    }
    public void removeCommentaire(Commentaire commentaire){
        this.commentaires.remove(commentaire);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Commentaire> getCommentaires(){
        return this.commentaires;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
