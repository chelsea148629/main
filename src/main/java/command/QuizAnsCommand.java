package command;

import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

public class QuizAnsCommand extends Command {
    protected String userAnswer;
    protected String answer;
    public QuizAnsCommand(String s){
        this.userAnswer = s;
    }

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage){
        answer = storage.loadTempQuizAns();
        //System.out.println("QuizAns "+ answer);
        if(userAnswer.equals(answer)){
            ui.quizResponse(true, answer);
        }
        else{
            ui.quizResponse(false, answer);
        }
        return;
    }
}
