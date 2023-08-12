package hb.exam.model;

import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date_commande", nullable = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Calendar dateCommande;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailsCommande> detailsCommandes;

    public Commande() {
        this.detailsCommandes = new ArrayList<>();
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;
    public Utilisateur getUtilisateur(){
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    public void setDateCommande(Calendar dateCommande) {
        this.dateCommande = dateCommande;
    }
}
