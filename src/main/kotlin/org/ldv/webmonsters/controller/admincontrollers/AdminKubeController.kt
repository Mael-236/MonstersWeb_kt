package org.ldv.webmonsters.controller.admincontrollers

import org.ldv.webmonsters.model.dao.KubeDAO
import org.springframework.stereotype.Controller

@Controller
class AdminKubeController (
    val kubeDAO: KubeDAO
) {
}