import exercice02.ServiceException;
import exercice02.UserService;
import exercice02.Utilisateur;
import exercice02.UtilisateurApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestExercice03 {
    @Mock
    private UtilisateurApi utilisateurApiMock;

    @Test
    public void testCreerUtilisateur() throws ServiceException {
        // Création d'un nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");


        // Scénario 1 :
        doThrow(new ServiceException("Echec de la création de l'utilisateur")).when(utilisateurApiMock).creerUtilisateur(utilisateur);


        // Scénario 2 :
        try {
            UserService userService = new UserService(utilisateurApiMock);
            userService.creerUtilisateur(utilisateur);
        } catch (ServiceException e) {
        }
        verify(utilisateurApiMock, never()).creerUtilisateur(utilisateur);


        // Scénario 3 :
        doReturn(true).when(utilisateurApiMock).creerUtilisateur(utilisateur);
        // Définition d'un ID fictif
        int idUtilisateur = 123;
        // Configuration du mock pour renvoyer l'ID
        doReturn(idUtilisateur).when(utilisateurApiMock).creerUtilisateur(utilisateur);
        // Appel de la méthode à tester
        UserService userService = new UserService(utilisateurApiMock);
        userService.creerUtilisateur(utilisateur);
        // Vérification de l'ID de l'utilisateur
        utilisateur.setId(idUtilisateur);
        verify(utilisateurApiMock).creerUtilisateur(utilisateur);


        // Scénario 4 :
        ArgumentCaptor<Utilisateur> argumentCaptor = ArgumentCaptor.forClass(Utilisateur.class);
        doReturn(true).when(utilisateurApiMock).creerUtilisateur(any(Utilisateur.class));
        userService.creerUtilisateur(utilisateur);
        Utilisateur utilisateurCapture = argumentCaptor.getValue();
        // Vérification des arguments capturés
        assertEquals(utilisateur.getNom(), utilisateurCapture.getNom());
        assertEquals(utilisateur.getPrenom(), utilisateurCapture.getPrenom());
        assertEquals(utilisateur.getEmail(), utilisateurCapture.getEmail());
    }
}