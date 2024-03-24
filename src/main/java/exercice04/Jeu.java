package exercice04;

public class Jeu {
    private Banque banque;
    private boolean ouvert;

    public Jeu(Banque banque) {
        this.banque = banque;
        this.ouvert = true;
    }

    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {
        if (!ouvert) {
            throw new JeuFermeException("Le jeu est fermé.");
        }

        if (!banque.est_solvable() || !joueur.est_solvable()) {
            ouvert = false;
            throw new JeuFermeException("La banque ou le joueur est insolvable.");
        }

        int mise = joueur.mise();
        try {
            joueur.debiter(mise);
            int sommeDes = de1.lancer() + de2.lancer();
            if (sommeDes == 7) {
                joueur.crediter(mise * 2);
                if (!banque.est_solvable()) {
                    ouvert = false;
                    throw new JeuFermeException("La banque n'est plus solvable après un gain.");
                }
            } else {
                ouvert = false;
            }
        } catch (DebitImpossibleException e) {
            ouvert = false;
            throw new JeuFermeException("Le joueur est insolvable.");
        }
    }

    public void fermer() {
        ouvert = false;
    }

    public boolean estOuvert() {
        return ouvert;
    }
}

