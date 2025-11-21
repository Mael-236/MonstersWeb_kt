package org.ldv.webmonsters.model.dao

import org.ldv.webmonsters.model.entity.Monstre
import org.springframework.data.jpa.repository.JpaRepository

interface MonstreDAO : JpaRepository<Monstre, Long> {
}