package foldermanager;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = Objects.requireNonNull(folders);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return streamAllIterative()
                .filter(f -> f.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return streamAllIterative()
                .filter(f -> f.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) streamAllIterative().count();
    }

    /**
     * Iterative flattening: for each folder, we
     * push it onto the stack and loop through it until
     * the stack is empty and
     * all nested folders have been taken from all nodes(MultiFolders) of the tree
     */
    private Stream<Folder> streamAllIterative() {
        List<Folder> all = new ArrayList<>();

        // for each folder, run the flattening process
        folders.forEach(folder -> {
            Deque<Folder> stack = new ArrayDeque<>();
            stack.push(folder);

            while (!stack.isEmpty()) {
                Folder currentFolder = stack.pop();
                all.add(currentFolder);

                if (currentFolder instanceof MultiFolder) {
                    // put all children on the stack
                    ((MultiFolder) currentFolder)
                            .getFolders()
                            .forEach(stack::push);
                }
            }
        });

        return all.stream();
    }
}