package com.avdhoot.orders.web.rest

import com.avdhoot.orders.security.getCurrentUserLogin
import com.fasterxml.jackson.annotation.JsonCreator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class AccountResource {

    private val log = LoggerFactory.getLogger(javaClass)

    internal class AccountResourceException : RuntimeException()

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws AccountResourceException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    fun getAccount(): UserVM {
        val login = getCurrentUserLogin()
            .orElseThrow { AccountResourceException() }
        val authorities = SecurityContextHolder.getContext().authentication.authorities
            .mapNotNullTo(mutableSetOf()) { it.authority }
        return UserVM(login, authorities)
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    fun isAuthenticated(request: HttpServletRequest): String? {
        log.debug("REST request to check if the current user is authenticated")
        return request.remoteUser
    }

    data class UserVM @JsonCreator constructor(val login: String, val authorities: Set<String>) {

        fun isActivated() = true
    }
}
