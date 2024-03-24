import exercice04.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JeuTest {

    @Test
    public void testFermerJeuShouldReturnFalse() {
        Banque banque = new BanqueImplementation();
        Jeu jeu = new Jeu(banque);

        jeu.fermer();

        assertFalse(jeu.estOuvert());
    }

    @Test
    public void testJoueurInsolvableShouldReturnFalse() {
        Banque banque = mock(Banque.class);
        Joueur joueur = mock(Joueur.class);
        De de1 = mock(De.class);
        De de2 = mock(De.class);

        when(banque.est_solvable()).thenReturn(true);
        when(joueur.est_solvable()).thenReturn(false);

        Jeu jeu = new Jeu(banque);

        try {
            jeu.jouer(joueur, de1, de2);
            fail("Exception JeuFermeException attendue.");
        } catch (JeuFermeException e) {
            assertEquals("La banque ou le joueur est insolvable.", e.getMessage());
            assertFalse(jeu.estOuvert());
        }
    }
}