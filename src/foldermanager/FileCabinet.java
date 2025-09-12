package foldermanager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = Objects.requireNonNull(folders);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return streamAll()
                .filter(f -> f.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return streamAll()
                .filter(f -> f.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) streamAll().count();
    }

    private Stream<Folder> streamAll() {
        return folders.stream().flatMap(this::flatten);
    }

    private Stream<Folder> flatten(Folder folder) {
        Stream<Folder> self = Stream.of(folder);
        if (folder instanceof MultiFolder mf) {
            return Stream.concat(self, mf.getFolders().stream().flatMap(this::flatten));
        }
        return self;
    }
}