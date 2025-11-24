package org.ldv.webmonsters.model.dao

import org.springframework.data.jpa.repository.JpaRepository

interface KubeDAO : JpaRepository<Kube, Long> {
}