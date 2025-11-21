package org.ldv.webmonsters.model.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.ldv.webmonsters.model.entity.Zone

interface ZoneDAO: JpaRepository<Zone, Long> {
}