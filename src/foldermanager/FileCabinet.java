package foldermanager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = Objects.requireNonNull(folders);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException();
    }
}