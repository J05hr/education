
//Joshua Rozenberg - jr922
//Kenneth Scholwinski - kjs270

package data;

public class SongNode implements Comparable<SongNode> {

	// Song detail, with name, artist, album, and year, of the song that is selected
	// in the song list interface
	private String name;
	private String artist;
	private String album;
	private String year;

	// empty node
	public SongNode() {
		this.name = "";
		this.artist = "";
		this.album = "";
		this.year = "";
	}

	public SongNode(String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.album = "";
		this.year = "";
	}

	public SongNode(String name, String artist, String album) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = "";
	}

	public SongNode(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	@Override
	public int compareTo(SongNode other) {
		return this.getId().compareToIgnoreCase(other.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SongNode songNode = (SongNode) o;

		if (name != null ? !name.equals(songNode.name) : songNode.name != null)
			return false;
		return artist != null ? artist.equals(songNode.artist) : songNode.artist == null;
	}

	public String getId() {
		return name + artist;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return name + " - " + artist;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (artist != null ? artist.hashCode() : 0);
		return result;
	}
}
