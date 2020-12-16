
//Joshua Rozenberg - jr922
//Kenneth Scholwinski - kjs270

package view;

import data.PersistenceManager;
import data.SongNode;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Optional;

public class SongLibController {

	@FXML
	ListView<SongNode> listView;
	public TextField songField;
	public TextField artistField;
	public TextField yearField;
	public TextField albumField;
	private ObservableList<SongNode> songList;
	private PersistenceManager manager = new PersistenceManager();

	public void start(Stage mainStage) {
		// create an ObservableList from an ArrayList
		songList = manager.loadSongData();

		// populate the list
		listView.setItems(songList);
		listView.getItems().sort(SongNode::compareTo);

		// set listener for the selection and fill the details
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> fillDetails());

		// select the first item
		listView.getSelectionModel().select(0);
	}

	private boolean confirmAction(String title, String header) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle(title);
		alert.setHeaderText(header);
		String content = "" + songField.getText() + "\n" + artistField.getText() + "\n" + albumField.getText() + "\n"
				+ yearField.getText() + "\n";
		alert.setContentText(content);
		Optional<ButtonType> result = alert.showAndWait();
		return (result.get() == ButtonType.OK);
	}

	private void warnTheUser(String title, String header) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.showAndWait();
	}

	// add a new item sorted
	public boolean addNewItem() {
		// check for a valid name and artist
		if (songField.getText().equals("") || artistField.getText().equals("")) {
			// warn the user
			warnTheUser("Beep Boop, Cannot Compute",
					"The Song or Artist name is invalid and won't be added. \n\nThese fields cannot be blank.");

			fillDetails();
			return false;
		}
		// create a newSong
		SongNode newSong = new SongNode(songField.getText(), artistField.getText());
		newSong.setAlbum(albumField.getText());

		// time to check if we have a valid year
		if (yearField.getText() == null || yearField.getText().equals("") || isValidYear(yearField.getText())) {
			newSong.setYear(yearField.getText());
		} else {
			// warn if date is out of range
			warnTheUser("Beep Boop, Cannot Compute",
					"The Year is invalid and won't be added. \n\nPlease enter a value between 0 and 2019 or leave it blank.");
			fillDetails();
			return false;
		}

		// ask for confirmation upon saving
		if (confirmAction("Please Confirm", "Are you sure you want to add this Song?")) {
			if (!listView.getItems().contains(newSong)) {
				listView.getItems().add(newSong);
				listView.getItems().sort(SongNode::compareTo);
				// select the newSong
				listView.getSelectionModel().select(newSong);
				manager.writeSongData(songList);
				return true;
			} else {
				// warn the user
				warnTheUser("Beep Boop, Cannot Compute",
						"This Song and Artist is already on the list and won't be added. \n\nPlease make sure the Song and Artist is unique.");
				fillDetails();
				return false;
			}
			// user canceled the add song
		} else {
			fillDetails();
			return false;
		}

	}

	// save the edits in details
	public boolean saveEdits() {

		// check for a valid name and artist
		if (songField.getText().equals("") || artistField.getText().equals("")) {
			// warn the user
			warnTheUser("Beep Boop, Cannot Compute",
					"The Song or Artist name is invalid and changes will not be saved. \n\nThese fields cannot be blank.");
			fillDetails();
			return false;
		}

		// time to check if we have a valid year
		if (yearField.getText() != null && !yearField.getText().equals("") && !isValidYear(yearField.getText())) {
			// warn the user if date is out of range
			warnTheUser("Beep Boop, Cannot Compute",
					"The Year is invalid and changes will not be saved. \n\nPlease enter a value between 0 and 2019 or leave it blank.");
			fillDetails();
			return false;
		}

		if (confirmAction("Save Edits", "Click OK to confirm or Cancel to revert these changes")) {
			// get the selection to edit
			SongNode oldItem = listView.getSelectionModel().getSelectedItem();
			// editing optional data, just change it
			if (oldItem.getId().compareToIgnoreCase(songField.getText() + artistField.getText()) == 0) {
				oldItem.setAlbum(albumField.getText());
				oldItem.setYear(yearField.getText());

				manager.writeSongData(songList);
				return true;
				// trying to change a name, need to check for dupes
			} else {
				if (!addNewItem()) {
					return false;
				}
				listView.getItems().remove(oldItem);
			}
			manager.writeSongData(songList);
			return true;
		}
		fillDetails();
		return false;
	}

	// simply remove the selected node
	public boolean deleteSelectedItem() {
		if (confirmAction("Confirm Deletion", "Are you sure you want to delete this Song and Artist?")) {
			SongNode item = listView.getSelectionModel().getSelectedItem();
			int itemIndex = listView.getSelectionModel().getSelectedIndex();
			if (listView.getItems() != null) {
				listView.getItems().remove(item);

				manager.writeSongData(songList);

				// change selection to make sure we select the right song after deletion
				// if(itemIndex == listView.)
				// thought i would need some index checking, but there are not errors and it
				// just works
				listView.getSelectionModel().select(itemIndex);
				listView.getFocusModel().focus(itemIndex);
				fillDetails();

			}
			return true;
		}
		return false;
	}

	private void fillDetails() {
		SongNode item = listView.getSelectionModel().getSelectedItem();
		if (item != null) {
			songField.setText(item.getName());
			artistField.setText(item.getArtist());
			albumField.setText(item.getAlbum());
			yearField.setText(item.getYear() == "0" ? null : String.valueOf(item.getYear()));
		} else {
			songField.clear();
			artistField.clear();
			albumField.clear();
			yearField.clear();
		}
	}

	private boolean isValidYear(String num) {
		// space or null is fine
		if (num == null || num.equals("")) {
			return true;
		}

		// see if it's anything not a number
		try {
			int i = Integer.parseInt(num);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}

		// check for valid range
		if (Integer.valueOf(num) <= 0 || Integer.valueOf(num) > 2019) {
			return false;
		}

		return true;
	}

//    private void showItem(Stage mainStage) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        //alert.initModality(Modality.NONE);
//        alert.initOwner(mainStage);
//        alert.setTitle("List Item");
//        alert.setHeaderText("Selected list item properties");
//
//        String content = "Index: " +
//                listView.getSelectionModel()
//                        .getSelectedIndex() +
//                "\nValue: " +
//                listView.getSelectionModel()
//                        .getSelectedItem();
//
//        alert.setContentText(content);
//        alert.showAndWait();
//        // alert.show();
//        //System.out.println("hey there!");
//    }
//
//    private void showItemInputDialog(Stage mainStage) {
//        SongNode item = listView.getSelectionModel().getSelectedItem();
//        int index = listView.getSelectionModel().getSelectedIndex();
//
//        TextInputDialog dialog = new TextInputDialog(item.toString());
//        dialog.initOwner(mainStage); dialog.setTitle("List Item");
//        dialog.setHeaderText("Selected Item (Index: " + index + ")");
//        dialog.setContentText("Enter name: ");
//
//        //TODO: When there's more fields than names this needs to be updated
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) { songList.set(index, new SongNode(result.get(), "")); }
//    }

}
