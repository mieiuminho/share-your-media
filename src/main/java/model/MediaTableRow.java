package model;

public class MediaTableRow implements Comparable<MediaTableRow> {
    private String name;
    private String artist;
    private String album;
    private String series;
    private String category1;
    private String category2;
    private String category3;

    public MediaTableRow(String name, String artist, String album, String series, String category1, String category2,
                         String category3) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.series = series;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    @Override
    public int compareTo(MediaTableRow o) {
        return (this.getArtist() + this.getName()).compareTo(o.getArtist() + o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaTableRow that = (MediaTableRow) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getArtist() != null ? !getArtist().equals(that.getArtist()) : that.getArtist() != null) return false;
        if (getAlbum() != null ? !getAlbum().equals(that.getAlbum()) : that.getAlbum() != null) return false;
        if (getSeries() != null ? !getSeries().equals(that.getSeries()) : that.getSeries() != null) return false;
        if (getCategory1() != null ? !getCategory1().equals(that.getCategory1()) : that.getCategory1() != null)
            return false;
        if (getCategory2() != null ? !getCategory2().equals(that.getCategory2()) : that.getCategory2() != null)
            return false;
        return getCategory3() != null ? getCategory3().equals(that.getCategory3()) : that.getCategory3() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getArtist() != null ? getArtist().hashCode() : 0);
        result = 31 * result + (getAlbum() != null ? getAlbum().hashCode() : 0);
        result = 31 * result + (getSeries() != null ? getSeries().hashCode() : 0);
        result = 31 * result + (getCategory1() != null ? getCategory1().hashCode() : 0);
        result = 31 * result + (getCategory2() != null ? getCategory2().hashCode() : 0);
        result = 31 * result + (getCategory3() != null ? getCategory3().hashCode() : 0);
        return result;
    }
}
