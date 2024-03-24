package exercice04;

public class BanqueImplementation implements Banque{
    private int fond;
    private int fondMinimum = 0;

    public BanqueImplementation() {
        this.fond = fond;
        this.fondMinimum = fondMinimum;
    }

    @Override
    public void crediter(int somme) {
        fond += somme;
    }

    @Override
    public boolean est_solvable() {
        return fond >= fondMinimum;
    }

    @Override
    public void debiter(int somme) {
        fond -= somme;
    }
}
