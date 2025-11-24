package org.ldv.webmonsters.model.entity

import org.ldv.webmonsters.model.entity.Monstre
import jakarta.persistence.*

@Entity
class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null  // Add this as the actual primary key

    @ManyToOne
    @JoinColumn(name = "monstre_actif_id")
    var monsActif: Monstre? = null  // Remove @Id from here
    val tailleMax: Int = 6
    private val Monstres = mutableListOf<Monstre>()

    fun ajouterMonstre(monstre: Monstre): Boolean {
        return if (!estPleine()) {
            Monstres.add(monstre)
            if (monsActif == null) {
                monsActif = monstre
            }
            println("${monstre.nom} ajouté à l'équipe")
            true
        } else {
            println("L'équipe est pleine!")
            false
        }
    }

    fun retirerMonstre(monstre: Monstre) {
        if (Monstres.remove(monstre)) {
            if (monsActif == monstre) {
                monsActif = Monstres.firstOrNull()
            }
            println("${monstre.nom} retiré de l'équipe")
        }
    }

    fun changerMonstreActif(monstre: Monstre) {
        if (monstre in Monstres) {
            monsActif = monstre
            println("${monstre.nom} est maintenant actif")
        }
    }

    fun estPleine(): Boolean = Monstres.size >= tailleMax

    fun getMonstres(): List<Monstre> = Monstres.toList()
}