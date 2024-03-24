package exercice02;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String email;
    private int id;

    public Utilisateur(String nom, String prenom, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = mail;
    }

    public void setId(int idUtilisateur) {
        this.id = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
