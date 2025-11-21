package org.ldv.webmonsters.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {

    @GetMapping("/webmonsters/admin")
    fun index(): String {
        return "pageAdmin/index"
    }
}