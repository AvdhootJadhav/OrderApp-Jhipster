package com.avdhoot.orders

import com.avdhoot.orders.OrderApp
import com.avdhoot.orders.config.AsyncSyncConfiguration

import org.springframework.test.annotation.DirtiesContext
import org.springframework.boot.test.context.SpringBootTest

/**
 * Base composite annotation for integration tests.
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [OrderApp::class, AsyncSyncConfiguration::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
annotation class IntegrationTest {
}
