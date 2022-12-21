	  
	  
	  
	  
	  
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
val kbuildLibsFolder  = sourceFile!!.parentFile.resolve("lib")
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