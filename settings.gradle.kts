import matt.kbuild.glang.settings.MySettingsPlugin
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

buildscript {

    val props by lazy {
        java.util.Properties().apply {
            load(
                sourceFile!!.parentFile.resolve("gradle.properties").reader()
            )
        }
    }

    fun prop(key: String) = (gradle.startParameter.projectProperties[key] ?: props[key].toString())
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()/*maven(url = "https://androidx.dev/storage/compose-compiler/repository/")*/
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }/*this is necessary for libs.xmlutil.core and libs.xmlutil.serialization*/
    val androidAttribute = Attribute.of(
        "net.devrieze.android",
        Boolean::class.javaObjectType
    )
    configurations.all {
        attributes {
            attribute(
                androidAttribute,
                false
            )
        }
    }


    dependencies {

        val osName = System.getProperty("os.name")
        val userHomeFolder = File(System.getProperty("user.home"))
        val registeredDir = userHomeFolder.resolve("registered")

        class Dep(
            val group: String,
            val name: String,
            val version: String
        ) {

            override fun toString(): String {
                return "$group:$name:$version"
            }
        }

        val depsSeen = mutableListOf<Dep>()

        val pluginsXMLFile = settings.rootDir.resolve("plugins.xml")

        val xml = pluginsXMLFile.readText()
        val content = xml.substringAfter(">").substringBeforeLast("<")
        var pluginsList = content.split("</")
        pluginsList = pluginsList.subList(
            0,
            pluginsList.size - 1
        )
        pluginsList = pluginsList.map {
            if (it.count { it == '>' } == 2) {
                it.substringAfter(">")
            } else {
                it
            }
        }.map {
            it.trim()
        }

        class Plugin(
            val name: String,
            var version: String? = null
        )

        val realPluginsList = pluginsList.map {
            val name = it.substringAfter(">").trim()
            Plugin(name).apply {
                val attrsString = it.substringBefore(">")
                if ("version" in attrsString) {
                    var gettingCloser = attrsString.substringAfter("version").substringAfter("=")
                    if ("apply" in gettingCloser) {
                        gettingCloser = gettingCloser.substringBefore("apply")
                    }
                    version = gettingCloser.replace(
                        "\"",
                        ""
                    ).replace(
                        "'",
                        ""
                    ).trim()
                }
            }
        }

        realPluginsList.forEach { gradleMod ->
            val kbuildLibsFolder = if (gradleMod.version == null) {
                val kbuildDir = registeredDir.resolve("gbuild/dist/${gradleMod.name}")
                val numBack = prop("NUM_BACK").toInt()
                if (osName == "Windows 11") {
                    error("not ready")
                } else {

                    val recentVersion = kbuildDir.list()!!.mapNotNull {
                        it.toLongOrNull()
                    }.sorted().reversed()[numBack]
                    kbuildDir.resolve("$recentVersion")

                }
            } else {

                val kbuildVersion = gradleMod.version!!
                val mattCacheFolder = rootDir.resolve(".gradle").resolve("matt").resolve(gradleMod.name)
                val downloadedKbuildVersionFile = mattCacheFolder.resolve("kbuildVersion.txt")
                val myKbuildLibFolder = mattCacheFolder.resolve("lib")
                if (!downloadedKbuildVersionFile.exists() || downloadedKbuildVersionFile.readText() != kbuildVersion) {
                    fun readBytesFromURL(url: String): ByteArray {
                        val connection = uri(url).toURL().openConnection()
                        val inputStream = connection.getInputStream()
                        val bytes = inputStream.readAllBytes()
                        inputStream.close()
                        return bytes
                    }
                    println("maybe deleting old folders")
                    if (mattCacheFolder.exists()) mattCacheFolder.deleteRecursively()
                    myKbuildLibFolder.mkdirs()

                    println("Downloading Kbuild...")

                    val mcVersion = 0

                    val host = "https://matt-central.nyc3.digitaloceanspaces.com/"

                    val artifactURL = "$host/$mcVersion/${gradleMod.name}/$kbuildVersion"

                    println("getting list of raw files...")
                    val rawFilesText =
                        readBytesFromURL("$artifactURL/files.lsv").decodeToString()
                    println("getting list of refs...")
                    val refsText =
                        readBytesFromURL("$artifactURL/refs.csv").decodeToString()


                    println("downloading raw files...")


                    rawFilesText.lines().filter { it.isNotBlank() }.forEach {
                        println("downloading " + it.trim() + "...")
                        val url = "$artifactURL/${it.trim()}"
                        val os = myKbuildLibFolder.resolve(it.trim()).outputStream()
                        os.write(readBytesFromURL(url))
                        os.close()
                    }
                    refsText.lines().filter { it.isNotBlank() }.forEach {
                        val fileName = it.substringBefore(",")
                        val url = it.substringAfter(",")
                        println("downloading $fileName...")
                        val os = myKbuildLibFolder.resolve(fileName).outputStream()
                        os.write(readBytesFromURL(url))
                        os.close()
                    }



                    println("Good to go!")
                    downloadedKbuildVersionFile.writeText(kbuildVersion)
                }
                classpath(fileTree(myKbuildLibFolder))
                val deps = myKbuildLibFolder.resolve("deps.txt").readLines().filter { it.isNotBlank() }.map {
                    val parts = it.split(":")
                    Dep(
                        parts[0],
                        parts[1],
                        parts[2]
                    )
                }
                deps.forEach { dep ->
                    depsSeen.firstOrNull { it.group == dep.group && it.name == dep.name }?.let {
                        require(it.version == dep.version) {
                            "conflicting versions for ${dep.group}:${dep.name}"
                        }
                    } ?: run {
                        classpath(dep.toString())
                        depsSeen += dep
                    }
                }
                myKbuildLibFolder
            }

            val depsText = kbuildLibsFolder.resolve("deps.txt")


            classpath(fileTree(kbuildLibsFolder))

            val deps = depsText.readLines().filter { it.isNotBlank() }.map {
                val parts = it.split(":")
                Dep(
                    parts[0],
                    parts[1],
                    parts[2]
                )
            }
            deps.forEach { dep ->
                depsSeen.firstOrNull { it.group == dep.group && it.name == dep.name }?.let {
                    require(it.version == dep.version) {
                        "conflicting versions for ${dep.group}:${dep.name}"
                    }
                } ?: run {
                    classpath(dep.toString())
                    depsSeen += dep
                }
            }

        }

    }

}

val dbFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
val dBuilder: DocumentBuilder = dbFactory.newDocumentBuilder()
val doc: Document = dBuilder.parse(settings.rootDir.resolve("plugins.xml"))
val rootElement: Element = doc.documentElement
rootElement.normalize()
val pluginsToApply = mutableListOf<String>()
val childs = rootElement.childNodes
for (i in 0 until childs.length) {
    val n = childs.item(i)
    if (n!!.nodeType == Node.ELEMENT_NODE) {
        val e = (n as Element)
        val apply = e.getAttribute("apply").takeIf { it.isNotBlank() }?.toBoolean() ?: true
        if (apply) {
            pluginsToApply.add(e.textContent)
        }
    }
}

val plugins = java.util.ServiceLoader.load(MySettingsPlugin::class.java).stream().map { it.get() }.toList()
pluginsToApply.forEach { modName ->
    plugins.firstOrNull { it.modName == modName }?.applyTo(settings)
        ?: println("WARNING: could not find plugin $modName")
}