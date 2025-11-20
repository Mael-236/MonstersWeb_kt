import org.springframework.stereotype.Component
import org.ldv.webmonsters.model.dao.UtilisateurDAO
import org.ldv.webmonsters.model.entity.Utilisateur
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder

@Component
class DataInitializer(
    val utilisateurDAO: UtilisateurDAO,
    val  passwordEncoder: PasswordEncoder

): CommandLineRunner {
    override fun run(vararg args: String?) {

//Utilisateurs
        val admin = Utilisateur(
            id = null,
            pseudo = "Admin",
            email = "admin@admin.com",
            motDePasse = passwordEncoder.encode("admin123"), // mot de passe hashé
            role = "ADMIN"
        )

        val client = Utilisateur(
            id = null,
            pseudo = "Client",
            email = "client@client.com",
            motDePasse = passwordEncoder.encode("client123"), // mot de passe hashé
            role = "CLIENT"
        )
        utilisateurDAO.saveAll(listOf(admin, client))
        println("✅ Données initiales insérées : ${utilisateurDAO.count()} utilisateurs.")
    }
}