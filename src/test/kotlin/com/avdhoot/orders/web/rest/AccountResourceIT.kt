package com.avdhoot.orders.web.rest

import com.avdhoot.orders.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.avdhoot.orders.web.rest.TEST_USER_LOGIN

/**
 * Integration tests for the {@link AccountResource} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(value = TEST_USER_LOGIN)
@IntegrationTest
class AccountResourceIT {

    @Autowired
    private lateinit var restAccountMockMvc: MockMvc

    @Test
    @Throws(Exception::class)
    @WithMockUser(username = TEST_USER_LOGIN, authorities = [ADMIN])
    fun testGetExistingAccount() {
        restAccountMockMvc.perform(
            get("/api/account")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("\$.login").value(TEST_USER_LOGIN))
            .andExpect(jsonPath("\$.authorities").value(ADMIN))
    }

    @Test
    @WithUnauthenticatedMockUser
    @Throws(Exception::class)
    fun testNonAuthenticatedUser(){
        restAccountMockMvc.perform(get("/api/authenticate")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string(""))
    }

    @Test
    @Throws(Exception::class)
    fun testAuthenticatedUser() {
        restAccountMockMvc.perform(get("/api/authenticate")
            .with(request -> {
                request.setRemoteUser(TEST_USER_LOGIN)
                return request
            })
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string(TEST_USER_LOGIN))
    }
}
