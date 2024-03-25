package exercice04;

public class BanqueImplementationQuestion7 implements Banque {
    public int fond;
    private final int fondMinimum;

    Jeu jeu ;
    public BanqueImplementationQuestion7(int fondInitial, int fondMinimum) {
        this.fond = fondInitial;
        this.fondMinimum = fondMinimum;
    }

    @Override
    public void crediter(int somme) {
        fond += somme;
        if (fond < fondMinimum) {
            // La banque devient insolvable, fermer le jeu
            fond = fond - somme; // Annuler le crédit au joueur
            jeu.fermer(); // Assurez-vous que 'jeu' est accessible depuis la classe BanqueImplementation
        }
    }


    @Override
    public boolean est_solvable() {
        return fond > fondMinimum;
    }

    @Override
    public void debiter(int somme) {
        fond -= somme;
    }

    // Méthode pour créditer le joueur même si le fond passe en dessous du niveau minimum
    public void crediterMemeInsolvable(int somme) {
        fond += somme;
    }
}