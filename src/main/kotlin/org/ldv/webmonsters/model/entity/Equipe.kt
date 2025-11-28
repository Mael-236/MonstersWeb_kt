package org.ldv.webmonsters.model.entity

import jakarta.persistence.*

@Entity
class Equipe(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "monstre_actif_id")
    var monsActif: Monstre? = null,

    @Column(nullable = false)
    val tailleMax: Int = 6,

    @OneToMany(mappedBy = "equipe", cascade = [CascadeType.ALL])
    var monstres: MutableList<Monstre> = mutableListOf()
) {
    fun ajouterMonstre(monstre: Monstre): Boolean {
        return if (!estPleine()) {
            monstres.add(monstre)
            monstre.equipe = this
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
        if (monstres.remove(monstre)) {
            monstre.equipe = null
            if (monsActif == monstre) {
                monsActif = monstres.firstOrNull()
            }
            println("${monstre.nom} retiré de l'équipe")
        }
    }

    fun changerMonstreActif(monstre: Monstre) {
        if (monstre in monstres) {
            monsActif = monstre
            println("${monstre.nom} est maintenant actif")
        }
    }

    fun estPleine(): Boolean = monstres.size >= tailleMax


}