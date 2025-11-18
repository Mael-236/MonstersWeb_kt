package org.ldv.webmonsters.model.entity

class inventaire {
    var argent: Int = 0
    private val objets = mutableMapOf<objet, Int>()

    fun ajouterObjet(objet: objet, quantite: Int) {
        objets[objet] = (objets[objet] ?: 0) + quantite
        println("${objet.nom} x$quantite ajouté à l'inventaire")
    }

    fun retirerObjet(objet: objet, quantite: Int) {
        val quantiteActuelle = objets[objet] ?: 0
        if (quantiteActuelle >= quantite) {
            objets[objet] = quantiteActuelle - quantite
            if (objets[objet] == 0) {
                objets.remove(objet)
            }
            println("${objet.nom} x$quantite retiré de l'inventaire")
        }
    }

    fun contientObjet(objet: objet): Boolean {
        return objets.containsKey(objet) && objets[objet]!! > 0
    }

    fun getQuantite(objet: objet): Int {
        return objets[objet] ?: 0
    }
}