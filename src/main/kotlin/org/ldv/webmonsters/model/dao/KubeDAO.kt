package org.ldv.webmonsters.model.dao

import org.ldv.webmonsters.model.entity.Kube
import org.springframework.data.jpa.repository.JpaRepository

interface KubeDAO : JpaRepository<Kube, Long> {
}