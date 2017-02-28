package application;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import phonebook.PhoneBook;

import java.util.Optional;
import java.util.Set;

public class PhoneBookMenu extends MenuBar {
	private PhoneBook phoneBook;
	private NameListView nameListView;
	
	/** Creates the menu for the phone book application.
	 * @param phoneBook the phone book with names and numbers
	 * @param nameListView handles the list view for the names
	 */
	public PhoneBookMenu(PhoneBook phoneBook, NameListView nameListView) {
		this.phoneBook = phoneBook;
		this.nameListView = nameListView;

		final Menu menuPhoneBook = new Menu("PhoneBook");
		final MenuItem menuQuit = new MenuItem("Quit");
		menuQuit.setOnAction(e -> Platform.exit());
		menuPhoneBook.getItems().addAll(menuQuit);
	
		final Menu menuFind = new Menu("Find");
		
		final MenuItem menuShowAll = new MenuItem("Show All");
		menuShowAll.setOnAction(e -> showAll());
		menuFind.getItems().addAll(menuShowAll);

		final MenuItem menuFindNumbers = new MenuItem("Find Number(s)");
		menuFindNumbers.setOnAction(e -> findNumbers());
		menuFind.getItems().addAll(menuFindNumbers);

		final MenuItem menuFindNames = new MenuItem("Find Name(s)");
		menuFindNames.setOnAction(e -> findNames());
		menuFind.getItems().addAll(menuFindNames);

		final MenuItem menuFindPersons = new MenuItem("Find person(s)");
		menuFindPersons.setOnAction(e -> findPersons());
		menuFind.getItems().addAll(menuFindPersons);

	    getMenus().addAll(menuPhoneBook, menuFind);
  //    setUseSystemMenuBar(true);  // if you want operating system rendered menus, uncomment this line
	}

	
	private void showAll() {
		nameListView.fillList(phoneBook.names());
		nameListView.clearSelection();
	}

	//fungerar felaktigt, nu visar den numrerna dirket men den ska visa namnet och highlighta den istället
	private void findNumbers() {
		Optional<String> mayBeName = Dialogs.oneInputDialog("Find Numbers", "Name:", "Name:");
		if(mayBeName.isPresent()) {
			Set numbers = phoneBook.findNumbers(mayBeName.get());
			if(!numbers.isEmpty())
				nameListView.fillList(numbers);
			else  Dialogs.alert("Could not find", "Could not find", "Could not find number associated with " + mayBeName.get());
		}
		//nameListView.clearSelection();
        //behöver lägga till så att den selectar automatiskt de
	}

	private void findNames(){
		Optional<String> mayBeNumber = Dialogs.oneInputDialog("Find Names", "Number:", "Number:");
		if(mayBeNumber.isPresent()) {
			Set names = phoneBook.findNames(mayBeNumber.get());
			if(!names.isEmpty())
				nameListView.fillList(names);
			else  Dialogs.alert("Could not find", "Could not find", "Could not find name associated with " + mayBeNumber.get());
		}
		//nameListView.clearSelection();
	}

	//ska hitta personer baserat på namn men ska använda första bokstäverna, man behöver alltså inte skriva in hela namnet
	private  void findPersons(){

	}

}
