package com.samjakob.askama.utilities;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.samjakob.askama.domain.AskamaActionException;
import org.rust.cargo.project.model.CargoProjectsService;
import org.rust.cargo.project.workspace.CargoWorkspace;

import java.nio.file.Path;
import java.util.Optional;

public class PathUtilities {

    public static final String TEMPLATES_DIR_NAME = "templates";

    /**
     * Get the path to the cargo project package that contains the given file.
     *
     * <p>If the file cannot be resolved to a Cargo project package, or the virtual file system that the file belongs to
     * doesn't support {@link Path}, then an empty optional is returned.</p>
     *
     * @param project the IntelliJ project to search in.
     * @param file the file to search for.
     * @return the path to the cargo project package, or null if not found.
     */
    public static Optional<Path> getCargoProjectPackagePath(Project project, VirtualFile file) {
        return Optional.ofNullable(project.getService(CargoProjectsService.class).findPackageForFile(file))
                .map(CargoWorkspace.Package::getContentRoot)
                .map(VirtualFile::toNioPath)
                .map(Path::normalize);
    }

    /**
     * Resolves the file's Cargo package path then returns the path to the templates directory within that package.
     *
     * <p>This can return an empty optional for the reasons stated in
     * {@link #getCargoProjectPackagePath(Project, VirtualFile)}.</p>
     *
     * @param project the IntelliJ project to search in.
     * @param file the file to find the templates directory for.
     * @return the path to the templates directory, or an empty optional if not found.
     *
     * @see #getCargoProjectPackagePath(Project, VirtualFile)
     */
    public static Optional<Path> getTemplatesDirectoryPath(Project project, VirtualFile file) {
        return getCargoProjectPackagePath(project, file)
                .map(path -> path.resolve(TEMPLATES_DIR_NAME))
                .map(Path::normalize);
    }

    /**
     * Resolves the file's Cargo package path then returns the path to the templates directory within that package.
     *
     * <p>If the file is not in the templates directory, this will throw an {@link AskamaActionException}.</p>
     *
     * @param project the IntelliJ project to search in.
     * @param file the file to find the templates directory for.
     * @return the path (relativized to the templates directory).
     * @throws AskamaActionException If the file cannot be resolved to a Cargo project package, or the virtual file
     * system that the file belongs to doesn't support {@link Path}, or the file is not in the templates directory.
     */
    public static String resolveFileToTemplatesDirectory(Project project, VirtualFile file) throws AskamaActionException {
        final var templatesPath = getTemplatesDirectoryPath(project, file);

        if (templatesPath.isEmpty()) {
            throw new AskamaActionException("No Active Cargo Project", "No active cargo project could be found.");
        }

        try {
            final var absoluteTemplatesDir = templatesPath.get().toAbsolutePath();
            return absoluteTemplatesDir.relativize(file.toNioPath()).toString();
        } catch (UnsupportedOperationException e) {
            throw new AskamaActionException("Failed to Resolve File", "The active file could not be resolved in the file system.");
        }
    }

    public static boolean isFileInTemplatesDirectory(Project project, VirtualFile file) throws AskamaActionException {
        final var templatesPath = getTemplatesDirectoryPath(project, file);

        if (templatesPath.isEmpty()) {
            throw new AskamaActionException("No Active Cargo Project", "No active cargo project could be found.");
        }

        final var absoluteTemplatesDir = templatesPath.get().toAbsolutePath();

        try {
            final var absoluteFile = file.toNioPath().toAbsolutePath();
            return absoluteFile.startsWith(absoluteTemplatesDir);
        } catch (UnsupportedOperationException e) {
            throw new AskamaActionException("Failed to Resolve File", "The active file could not be resolved in the file system.");
        }
    }

}
