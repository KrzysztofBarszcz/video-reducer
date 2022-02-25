import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val outputDir = Paths.get("/home/krzysztof/Videos")

    val rootDirectory = Paths.get(args[0])

    Files.walkFileTree(rootDirectory, VideoVisitor(rootDirectory, outputDir))

}
