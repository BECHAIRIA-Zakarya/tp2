import exercice04.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void testBanqueInsolvableApresGainShouldThrowException()  {
        Banque banque = mock(Banque.class);
        Joueur joueur = mock(Joueur.class);
        De de1 = mock(De.class);
        De de2 = mock(De.class);


        when(joueur.est_solvable()).thenReturn(true);
        when(banque.est_solvable()).thenReturn(true, false);

        Jeu jeu = new Jeu(banque);

        // les dés donnent une somme de 7
        when(de1.lancer()).thenReturn(1);
        when(de2.lancer()).thenReturn(6);

        assertTrue(jeu.estOuvert());

        assertThrows(JeuFermeException.class, () -> jeu.jouer(joueur, de1, de2));

        assertFalse(jeu.estOuvert());
    }

    @Test
    public void testJoueurMiseSommeDesPasEgaleASeptShouldReturnFalse() {
        Banque banque = mock(Banque.class);
        Joueur joueur = mock(Joueur.class);
        De de1 = mock(De.class);
        De de2 = mock(De.class);

        when(banque.est_solvable()).thenReturn(true);
        when(joueur.est_solvable()).thenReturn(true);
        when(joueur.mise()).thenReturn(10);
        when(de1.lancer()).thenReturn(2);
        when(de2.lancer()).thenReturn(4);

        Jeu jeu = new Jeu(banque);

        try {
            jeu.jouer(joueur, de1, de2);
            verify(joueur, times(1)).debiter(10);
            verify(joueur, never()).crediter(anyInt());
            assertFalse(jeu.estOuvert());
        } catch (JeuFermeException | DebitImpossibleException e) {
            fail("Aucune exception n'était attendue.");
        }
    }

    @Test
    public void testJoueurMiseSommeDesEgaleASeptShouldReturnTrue() {
        Banque banque = mock(Banque.class);
        Joueur joueur = mock(Joueur.class);
        De de1 = mock(De.class);
        De de2 = mock(De.class);

        when(banque.est_solvable()).thenReturn(true);
        when(joueur.est_solvable()).thenReturn(true);
        when(joueur.mise()).thenReturn(10);
        when(de1.lancer()).thenReturn(3);
        when(de2.lancer()).thenReturn(4);

        Jeu jeu = new Jeu(banque);

        try {
            jeu.jouer(joueur, de1, de2);
            verify(joueur, times(1)).debiter(10);
            verify(joueur, times(1)).crediter(20);
            assertTrue(jeu.estOuvert());
        } catch (JeuFermeException | DebitImpossibleException e) {
            fail("Aucune exception n'était attendue.");
        }
    }

    @Test
    public void testFondMinimumApresPariGagnant() throws JeuFermeException {
        // Configuration initiale de la banque avec un fond initial de 1000 et un fond minimum de 500
        BanqueImplementationQuestion7 banque = new BanqueImplementationQuestion7(1000, 500);
        Joueur joueur = mock(Joueur.class);
        De de1 = mock(De.class);
        De de2 = mock(De.class);

        // Le joueur est solvable
        when(joueur.est_solvable()).thenReturn(true);

        // Configurer le comportement des dés pour que la somme soit égale à 7
        when(de1.lancer()).thenReturn(1);
        when(de2.lancer()).thenReturn(6);

        // Le joueur joue et gagne
        Jeu jeu = new Jeu(banque);
        jeu.jouer(joueur, de1, de2);

        // Le fond de la banque doit être égal à 2000 après le gain
        assertTrue(banque.est_solvable());
        assertEquals(1000, banque.fond);

        // Configurer le comportement des dés pour que la somme ne soit pas égale à 7
        when(de1.lancer()).thenReturn(2);
        when(de2.lancer()).thenReturn(5);

        // Le joueur joue à nouveau et gagne
        jeu.jouer(joueur, de1, de2);

        // Le fond de la banque passe en dessous du fond minimum, mais le joueur doit être crédité quand même
        assertFalse(banque.est_solvable());
        assertEquals(500, banque.fond);
    }



}