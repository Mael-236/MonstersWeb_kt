import org.springframework.stereotype.Component
import org.ldv.webmonsters.model.dao.UtilisateurDAO
import org.ldv.webmonsters.model.entity.Utilisateur
import org.springframework.boot.CommandLineRunner

@Component
class DataInitializer(
    val utilisateurDAO: UtilisateurDAO,
    val  passwordEncoder: PasswordEncoder

): CommandLineRunner {
    override fun run(vararg args: String?) {

//Utilisateurs
        val admin = Utilisateur(
            id = null,
            nom = "Super",
            prenom = "Admin",
            email = "admin@admin.com",
            mdp = passwordEncoder.encode("admin123"), // mot de passe hashé
            role = "ADMIN"
        )

        val client = Utilisateur(
            id = null,
            nom = "Jean",
            prenom = "Client",
            email = "client@client.com",
            mdp = passwordEncoder.encode("client123"), // mot de passe hashé
            role = "CLIENT"
        )
        utilisateurDAO.saveAll(listOf(admin, client))
        println("✅ Données initiales insérées : ${roleDAO.count()} Roles, ${utilisateurDAO.count()} utilisateurs.")
    }
}