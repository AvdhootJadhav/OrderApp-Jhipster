package com.avdhoot.orders

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule

import com.tngtech.archunit.base.DescribedPredicate.alwaysTrue
import com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf
import com.tngtech.archunit.library.Architectures.layeredArchitecture


@AnalyzeClasses(packagesOf = [OrderApp::class], importOptions = [DoNotIncludeTests::class])
class TechnicalStructureTest {
        @ArchTest
        val respectsTechnicalArchitectureLayers = layeredArchitecture()
            .layer("Config").definedBy("..config..")
            .layer("Web").definedBy("..web..")
            .optionalLayer("Service").definedBy("..service..")
            .layer("Security").definedBy("..security..")
    
            .whereLayer("Config").mayNotBeAccessedByAnyLayer()
            .whereLayer("Web").mayOnlyBeAccessedByLayers("Config")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web", "Config")
            .whereLayer("Security").mayOnlyBeAccessedByLayers("Config", "Service", "Web")
    
            .ignoreDependency(belongToAnyOf(OrderApp::class.java), alwaysTrue())
            .ignoreDependency(alwaysTrue(), belongToAnyOf(
                com.avdhoot.orders.config.ApplicationProperties::class.java
            ))
}
