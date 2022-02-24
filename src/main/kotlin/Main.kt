import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

fun main(args: Array<String>) {
    val directory = Paths.get(args[0])
    Files.walkFileTree(directory, object : FileVisitor<Path> {
        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            println("Processing ${file.toString()}")
            return FileVisitResult.CONTINUE
        }

        override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            println("Visits ${dir.toString()}")
            return FileVisitResult.CONTINUE
        }

        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
            TODO("Not yet implemented")
        }

        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
            println("Finished ${dir.toString()}")
            return FileVisitResult.CONTINUE
        }

    })

}