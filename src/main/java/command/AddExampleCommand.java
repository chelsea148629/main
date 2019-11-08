package command;

import dictionary.Bank;
import dictionary.Word;
import exception.WordAlreadyExistsException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command from user to add a task.
 * Inherits from Command class.
 */
public class AddExampleCommand extends Command {
    public AddExampleCommand(Word w) {
        word = w;
    }

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {

        try {
            bank.addWordToExampleBank(word);
            storage.writeWordBankExcelFile(bank.getExampleBankObject(), 2);
            return ui.showAdded(word);
        } catch (WordAlreadyExistsException e) {
            return e.showError();
        }
        //storage.writeFile(word.toString(), true);
    }
}