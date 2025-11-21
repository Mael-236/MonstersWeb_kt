package org.ldv.webmonsters.model.dao

import org.ldv.webmonsters.model.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminDAO: JpaRepository<Admin, Long> {
}