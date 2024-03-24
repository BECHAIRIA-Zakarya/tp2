package exercice04;

public interface InterfaceJeu {
    public void Jeu(Banque labanque);



    public default void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {

    }

    public void fermer();
    public boolean estOuvert();
}
