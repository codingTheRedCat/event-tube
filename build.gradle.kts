plugins {
    id("net.kyori.indra") version "2.1.1"
    id("net.kyori.indra.checkstyle") version "2.1.1"
    id("net.kyori.indra.license-header") version "2.1.1"
    id("net.kyori.indra.publishing") version "2.1.1"
}

group = "net.taigamc"
version = "1.0.0"

indra {
    javaVersions {
        target(16)
        testWith(16)
    }

    gpl3OnlyLicense()

    publishReleasesTo("taigamc", "https://repo.taigamc.net/repository/maven-releases/")
    publishSnapshotsTo("taigamc", "https://repo.taigamc.net/repository/maven-snapshots/")

    configurePublications {
        pom {
            developers {
                developer {
                    id.set("codingTheRedCat")
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    checkstyle("ca.stellardrift:stylecheck:0.1")
}