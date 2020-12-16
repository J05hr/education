
//Joshua Rozenberg - jr922
//Kenneth Scholwinski - kjs270

package data;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * This will read/write json to the songLib.json file in the compiler output
 * path, which for IntelliJ is set in File -> Project Structure -> Project ->
 * Project compiler output
 *
 *
 * We are using
 *
 * Gson https://github.com/google/gson
 *
 * Download
 *
 * Gradle: dependencies { implementation 'com.google.code.gson:gson:2.8.5' }
 *
 * Maven: <dependency> <groupId>com.google.code.gson</groupId>
 * <artifactId>gson</artifactId> <version>2.8.5</version> </dependency>
 *
 *
 * from:
 * https://maven-badges.herokuapp.com/maven-central/com.google.code.gson/gson
 *
 *
 *
 *
 */
public class PersistenceManager {

	private static final String SONG_LIB_FILE_NAME = "songLib.json";

	public ObservableList<SongNode> loadSongData() {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		try {
			InputStream is = classloader.getResourceAsStream(SONG_LIB_FILE_NAME);
			Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
		} catch (Exception e) {

			// cant find the file, trying to load sample songs
			writeSongData(loadSampleSongs());

		}

		InputStream is = classloader.getResourceAsStream(SONG_LIB_FILE_NAME);
		Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

		Gson gson = new Gson();
		SongNode[] songs = gson.fromJson(reader, SongNode[].class);

		return FXCollections.observableArrayList(songs);
	}

	public void writeSongData(ObservableList<SongNode> songList) {
		Gson gson = new Gson();
		String json = gson.toJson(songList);

		URL urlFile = getClass().getClassLoader().getResource(SONG_LIB_FILE_NAME);

		try {
			File file = new File(urlFile.toURI());
			PrintWriter writer = new PrintWriter(file);
			writer.write(json);
			writer.close(); // must close or the UI doesn't load when running a second time
		} catch (Exception e) {
			throw new RuntimeException("Unable to write to file " + SONG_LIB_FILE_NAME, e);
		}
	}

	private ObservableList<SongNode> loadSampleSongs() {

		ObservableList<SongNode> songList = FXCollections.observableArrayList(
				new SongNode("How You Remind Me", "Nickelback", "Silver Side Up", "2001"),
				new SongNode("Thriller", "Michael Jackson", "Off the Wall", "1979"),
				new SongNode("All You Need Is Love", "The Beatles", "", "1967"),
				new SongNode("Happy Birthday", "Everyone", "", ""));

		return songList;
	}

}
