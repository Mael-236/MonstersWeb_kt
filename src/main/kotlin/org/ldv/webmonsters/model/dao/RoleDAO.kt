package org.ldv.webmonsters.model.dao

import org.ldv.webmonsters.model.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleDAO : JpaRepository<Role, Long> {
    fun findByNom(nom: String): Role?
}