package org.ldv.webmonsters

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebmonstersApplication

fun main(args: Array<String>) {
    runApplication<WebmonstersApplication>(*args)
}
