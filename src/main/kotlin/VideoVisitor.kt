import java.io.IOException
import java.lang.RuntimeException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

class VideoVisitor(private val rootDirectory: Path, private val outputDir: Path) : FileVisitor<Path> {

    override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
        val outputPath = file?.let { createOutputPath(it) } ?: throw RuntimeException("Dir is null")

        if (!Files.exists(outputPath)) {
            println("Processing $file")
            val conversionProcess = ProcessBuilder("ffmpeg", "-i", "$file", "-b", "1000000", "$outputPath")
                .start()
            conversionProcess.waitFor()
        } else {
            println("$file already exists")
        }
        return FileVisitResult.CONTINUE
    }

    override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
        println("Visits ${dir.toString()}")
        val outputPath = dir?.let { createOutputPath(it) } ?: throw RuntimeException("Dir is null")
        if (!Files.exists(outputPath)) {
            Files.createDirectory(outputPath)
            println("$outputPath created")
        } else {
            println("$outputPath exists")
        }

        return FileVisitResult.CONTINUE
    }

    override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
        TODO("Not yet implemented")
    }

    override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
        println("Finished ${dir.toString()}")
        return FileVisitResult.CONTINUE
    }

    private fun createOutputPath(dir: Path): Path {
        val relativePath = rootDirectory.relativize(dir)
        return outputDir.resolve(relativePath)
    }
}