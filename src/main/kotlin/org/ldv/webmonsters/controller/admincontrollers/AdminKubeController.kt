package org.ldv.webmonsters.controller.admincontrollers

import org.ldv.webmonsters.model.dao.KubeDAO
import org.ldv.webmonsters.model.entity.Kube
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/webmonsters/admin/kubes")
class AdminKubeController(
    val kubeDAO: KubeDAO
) {

    @GetMapping
    fun index(model: Model): String {
        val kubes = kubeDAO.findAll()
        model.addAttribute("kubes", kubes)
        return "pageAdmin/kube/index"
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        model.addAttribute("kube", Kube())
        return "pageAdmin/kube/create"
    }

    @PostMapping("/store")
    fun store(@ModelAttribute kube: Kube, redirectAttributes: RedirectAttributes): String {
        kubeDAO.save(kube)
        redirectAttributes.addFlashAttribute("success", "Kube créé avec succès !")
        return "redirect:/webmonsters/admin/kubes"
    }

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val kube = kubeDAO.findById(id).orElse(null)
        model.addAttribute("kube", kube)
        return "pageAdmin/kube/show"
    }

    @GetMapping("/edit/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val kube = kubeDAO.findById(id).orElse(null)
        model.addAttribute("kube", kube)
        return "pageAdmin/kube/edit"
    }

    @PostMapping("/update/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute kube: Kube, redirectAttributes: RedirectAttributes): String {
        kube.id = id
        kubeDAO.save(kube)
        redirectAttributes.addFlashAttribute("success", "Kube modifié avec succès !")
        return "redirect:/webmonsters/admin/kubes"
    }

    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String {
        kubeDAO.deleteById(id)
        redirectAttributes.addFlashAttribute("success", "Kube supprimé avec succès !")
        return "redirect:/webmonsters/admin/kubes"
    }
}