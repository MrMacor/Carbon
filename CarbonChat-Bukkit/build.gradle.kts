import java.nio.charset.StandardCharsets

plugins {
  id 'com.github.johnrengelman.shadow' version '6.1.0'
  id 'net.minecrell.plugin-yml.bukkit' version '0.3.0'
}

description = 'CarbonChat-Bukkit'

dependencies {
  implementation project(":CarbonChat-API")

  // Server
  compileOnly "com.destroystokyo.paper:paper-api:${vers['paper-api']}"

  // Adventure
  implementation "net.kyori:adventure-platform-bukkit:${vers['adventure-platform']}"
  implementation "net.kyori:adventure-text-serializer-bungeecord:${vers['adventure-platform']}"

  // Config
  implementation "org.spongepowered:configurate-yaml:${vers['configurate']}"
  implementation "org.yaml:snakeyaml:${vers['snakeyaml']}"

  // Commands
  implementation "cloud.commandframework:cloud-paper:${vers['cloud']}"
  
  // SLF4J Binding
  implementation 'org.slf4j:slf4j-jdk14:1.7.28'

  // Plugins
  compileOnly "me.clip:placeholderapi:${vers['placeholder-api']}"
  compileOnly "net.luckperms:api:${vers['luckperms-api']}"
  compileOnly "com.github.MilkBowl:VaultAPI:${vers['vault']}"
}

// Generates plugin.yml automatically
bukkit {
  name = "CarbonChat"
  version = rootProject.version
  main = 'net.draycia.carbon.bukkit.CarbonChatBukkit'
  apiVersion = '1.13'
  author = 'Draycia'
  depend = ['PlaceholderAPI', 'Vault', 'LuckPerms']
  loadBefore = ["Essentials"]
  permissions {
    'carbonchat.channels.global.see' {
      setDefault("TRUE")
    }

    'carbonchat.channels.global.use' {
      setDefault("TRUE")
    }
  }
}

checkstyle {
  def configRoot = new File(rootProject.projectDir, '.checkstyle')
  toolVersion = vers['checkstyle']
  configDirectory = configRoot
  configProperties = [basedir: configRoot.getAbsolutePath()]
}

// Set output directory and encoding
tasks.withType(JavaCompile) {
  options.encoding = StandardCharsets.UTF_8.name()
  options.compilerArgs += ['-Xlint:all']
}

// Automatically shadowJar when building
tasks.build.dependsOn tasks.shadowJar

// Output a single jar
shadowJar {
  minimize()
  archiveFileName.set(project.description + "-" + project.property('projectVersion') + ".jar")
  destinationDirectory = rootProject.getBuildDir()
  relocate("org.yaml", "net.draycia.carbon.libs.org.yaml")
  relocate("net.kyori", "net.draycia.carbon.libs.kyori")
  relocate("org.spongepowered.configurate", "net.draycia.carbon.libs.configurate")
  relocate("org.checkerframework", "net.draycia.carbon.libs.checkerframework")
  relocate("org.reactivestreams", "net.draycia.carbon.libs.reactivestreams")
  relocate("org.codehaus", "net.draycia.carbon.libs.codehaus")
  relocate("org.slf4j", "net.draycia.carbon.libs.slf4j")
  relocate("io.leangen.geantyref", "net.draycia.carbon.libs.typereference")
  relocate("io.lettuce", "net.draycia.carbon.libs.lettuce")
  relocate("co.aikar.idb", "net.draycia.carbon.libs.idb")
  relocate("cloud.commandframework", "net.draycia.carbon.libs.cloud")
  relocate("com.zaxxer.hikari", "net.draycia.carbon.libs.hikari")
  relocate("com.typesafe.config", "net.draycia.carbon.libs.typesafe.config")
  relocate("com.google.common", "net.draycia.carbon.libs.google.common")
  relocate("reactor", "net.draycia.carbon.libs.reactor")
  relocate("javax.annotation", "net.draycia.carbon.libs.javax.annotation")
}

jar {
  archiveFileName.set(project.description + "-" + project.property('projectVersion') + ".jar")
}

//// Automatically relocate all shaded dependencies
//import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
//
//task relocateShadowJar(type: ConfigureShadowRelocation) {
//  target = tasks.shadowJar
//  prefix = "net.draycia.carbon.libs" // Default value is "shadow"
//}
//
//tasks.shadowJar.dependsOn tasks.relocateShadowJar

// Cleanup custom output directory on clean task
clean.doFirst {
  delete "$rootDir/build/bundled"
}
