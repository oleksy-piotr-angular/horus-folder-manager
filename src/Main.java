import foldermanager.*;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Prepare sample folder hierarchy
        List<Folder> roots = List.of(
                new SimpleFolder("docs", "SMALL"),
                new CompoundFolder("projects", "LARGE", List.of(
                        new SimpleFolder("alpha", "MEDIUM"),
                        new SimpleFolder("beta", "MEDIUM")
                ))
        );

        Cabinet cabinet = new FileCabinet(roots);

        // 1. findFolderByName
        Optional<Folder> found = cabinet.findFolderByName("beta");
        if (found.isPresent()) {
            Folder f = found.get();
            System.out.println("Found folder: " + f.getName() + " [" + f.getSize() + "]");
        } else {
            System.out.println("Folder not found");
        }

        // 2. findFoldersBySize
        List<Folder> mediumFolders = cabinet.findFoldersBySize("MEDIUM");
        System.out.println("Folders with size MEDIUM:");
        mediumFolders.forEach(f -> System.out.println("  - " + f.getName()));

        // 3. count
        System.out.println("Total number of folders: " + cabinet.count());
    }

    // Simple class implementation of Folder interface
    static class SimpleFolder implements Folder {
        private final String name;
        private final String size;

        public SimpleFolder(String name, String size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSize() {
            return size;
        }
    }

    // Simple class implementation of MultiFolder interface
    static class CompoundFolder implements MultiFolder {
        private final String name;
        private final String size;
        private final List<Folder> children;

        public CompoundFolder(String name, String size, List<Folder> children) {
            this.name = name;
            this.size = size;
            this.children = children;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSize() {
            return size;
        }

        @Override
        public List<Folder> getFolders() {
            return children;
        }
    }
}