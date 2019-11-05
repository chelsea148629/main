package command;

import dictionary.Bank;
import dictionary.Word;
import exception.NoWordFoundException;
import exception.WordBankEmptyException;
import exception.WordCountEmptyException;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 */
public class SearchCommand extends Command {

    protected String searchTerm;

    public SearchCommand(String queryWord) {
        this.searchTerm = queryWord;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        try {
            String meaning = bank.searchWordBankForMeaning(this.searchTerm);
            bank.increaseSearchCount(searchTerm);
            return ui.showSearch(this.searchTerm, meaning);
        } catch (NoWordFoundException e) {
            StringBuilder stringBuilder = new StringBuilder();
            //Look up Oxford dictionary.
            stringBuilder.append("Unable to locate \"" + searchTerm
                    + "\" in local dictionary.\nLooking up Oxford dictionary.\n\n");
            try {
                String result = OxfordCall.onlineSearch(searchTerm);
                Word w = new Word(searchTerm, result);
                AddCommand addCommand = new AddCommand(w);
                addCommand.execute(ui, bank, storage);
                bank.increaseSearchCount(searchTerm);
                stringBuilder.append(ui.showSearch(this.searchTerm, result));
            } catch (NoWordFoundException | WordCountEmptyException e2) {
                stringBuilder.append("Failed to find the word from Oxford dictionary.\n");
            }

            // Spell checking. Look up similar words from local dictionary.
            ArrayList<String> arrayList = bank.getClosedWords(this.searchTerm);
            if (arrayList.size() > 0) {
                stringBuilder.append("\nAre you looking for these words instead?\n");
            }
            for (int i = 0; i < arrayList.size(); i++) {
                stringBuilder.append(arrayList.get(i) + "\n");
            }
            return e.showError() + stringBuilder;
        } catch (WordBankEmptyException | WordCountEmptyException e) {
            return e.showError();
        }
    }
}