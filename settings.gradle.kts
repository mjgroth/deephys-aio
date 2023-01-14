	  
	  
	  
	  
	  
	  import matt.kbuild.settings.applySettings
buildscript { 
val props  by lazy {java.util.Properties().apply {
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
}
/*this is necessary for libs.xmlutil.core and libs.xmlutil.serialization*/
val androidAttribute  = Attribute.of("net.devrieze.android", Boolean::class.javaObjectType)
configurations.all { 
attributes { 
attribute(androidAttribute, false)
}
}
dependencies { 
val osName  = System.getProperty("os.name")
val userHomeFolder  = File(System.getProperty("user.home"))
val registeredDir  = userHomeFolder.resolve("registered")
 class Dep(val group: String, val name: String, val version: String) {
 override fun toString(): String {
return "$group:$name:$version"
}
}
val depsSeen  = mutableListOf<Dep>()
listOf("kbuild").forEach { gradleMod ->
val kbuildVersion  = "1673722120264"
val mattCacheFolder  = rootDir.resolve(".gradle").resolve("matt")
val downloadedKbuildVersionFile  = mattCacheFolder.resolve("kbuildVersion.txt")
val kbuildLibsFolder  = mattCacheFolder.resolve("lib")
if (!downloadedKbuildVersionFile.exists() || downloadedKbuildVersionFile.readText() != kbuildVersion) {
  fun readBytesFromURL(url: String): ByteArray {
val connection  = uri(url).toURL().openConnection()
val inputStream  = connection.getInputStream()
val bytes  = inputStream.readAllBytes()
inputStream.close()
return bytes
}
println("maybe deleting old folders")
if (mattCacheFolder.exists()) mattCacheFolder.deleteRecursively()
kbuildLibsFolder.mkdirs()
  
println("Downloading Kbuild...")
val kbuildURL = "https://matt-central.nyc3.digitaloceanspaces.com//1/kbuild/$kbuildVersion/files.lsv"
println("getting list of raw files...")
val rawFilesText = readBytesFromURL("https://matt-central.nyc3.digitaloceanspaces.com//1/kbuild/$kbuildVersion/files.lsv").decodeToString()
println("getting list of refs...")
val refsText = readBytesFromURL("https://matt-central.nyc3.digitaloceanspaces.com//1/kbuild/$kbuildVersion/refs.csv").decodeToString()


println("downloading raw files...")


rawFilesText.lines().filter{it.isNotBlank()}.forEach {
	println("downloading " + it.trim() + "...")
	val url = "https://matt-central.nyc3.digitaloceanspaces.com//1/kbuild/$kbuildVersion/${it.trim()}"
	val os = kbuildLibsFolder.resolve(it.trim()).outputStream()
	os.write(readBytesFromURL(url))
	os.close()
}
refsText.lines().filter{it.isNotBlank()}.forEach {
	val fileName = it.substringBefore(",")
	val url = it.substringAfter(",")
	println("downloading " + fileName + "...")
	val os = kbuildLibsFolder.resolve(fileName).outputStream()
	os.write(readBytesFromURL(url))
	os.close()
}



println("Good to go!")
downloadedKbuildVersionFile.writeText(kbuildVersion)	
}
classpath(fileTree(kbuildLibsFolder))
val deps  =   kbuildLibsFolder.resolve("deps.txt").readLines().filter { it.isNotBlank() }.map {
  val parts = it.split(":")
  Dep(parts[0], parts[1], parts[2])
}
deps.forEach { dep ->
depsSeen.firstOrNull { it.group == dep.group && it.name == dep.name }?.let {
				require(it.version == dep.version) {
				  "conflicting versions for ${dep.group}:${dep.name}"
				}
			  }?:  run {
classpath(dep.toString())
depsSeen += dep
 }
}
}
}
}
applySettings()