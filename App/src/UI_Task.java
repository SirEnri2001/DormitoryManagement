package src;

import javafx.concurrent.Task;
import src.controller.student.StudentUI;

public class UI_Task extends Task<Void> {
    public StudentUI uiContainer = null;
    @Override
    protected Void call() throws Exception {
        uiContainer.InitController();
        return null;
    }
}
