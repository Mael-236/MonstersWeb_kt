package org.ldv.webmonsters.model.entity

import jakarta.persistence.*

@Entity
class Kube(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var nom: String = "",

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = false)
    var prix: Int = 0,

    @Column(nullable = false)
    var rarete: String = "Commun"
){

}