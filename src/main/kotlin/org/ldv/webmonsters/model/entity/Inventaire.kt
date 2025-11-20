package org.ldv.webmonsters.model.entity

class Inventaire {
    var argent: Int = 0
    private val objets = mutableMapOf<Objet, Int>()

    fun ajouterObjet(objet: Objet, quantite: Int) {
        objets[objet] = (objets[objet] ?: 0) + quantite
        println("${objet.nom} x$quantite ajouté à l'inventaire")
    }

    fun retirerObjet(objet: Objet, quantite: Int) {
        val quantiteActuelle = objets[objet] ?: 0
        if (quantiteActuelle >= quantite) {
            objets[objet] = quantiteActuelle - quantite
            if (objets[objet] == 0) {
                objets.remove(objet)
            }
            println("${objet.nom} x$quantite retiré de l'inventaire")
        }
    }

    fun contientObjet(objet: Objet): Boolean {
        return objets.containsKey(objet) && objets[objet]!! > 0
    }

    fun getQuantite(objet: Objet): Int {
        return objets[objet] ?: 0
    }
}