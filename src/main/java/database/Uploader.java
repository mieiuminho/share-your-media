package database;

import java.util.ArrayList;
import java.util.List;

public final class Uploader implements DataClass<String> {

    private String mediafileName;
    private String mediafileArtist;
    private String uploader;

    public Uploader(final String mediafileName, final String mediafileArtist, final String uploader) {
        this.mediafileName = mediafileName;
        this.mediafileArtist = mediafileArtist;
        this.uploader = uploader;
    }

    public Uploader(final List<String> l) {
        this.mediafileName = l.get(0);
        this.mediafileArtist = l.get(1);
        this.uploader = l.get(2);
    }

    public Uploader() {

    }

    public String getUploader() {
        return this.uploader;
    }

    public String getName() {
        return this.mediafileName;
    }

    public String getArtist() {
        return this.mediafileArtist;
    }

    public DataClass<String> fromRow(final List<String> l) {
        return new Uploader(l);
    }

    public List<String> toRow() {
        List<String> r = new ArrayList<>();
        r.add(this.mediafileName);
        r.add(this.mediafileArtist);
        r.add(this.uploader);
        return r;
    }
}
