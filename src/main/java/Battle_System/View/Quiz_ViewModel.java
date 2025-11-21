package Battle_System.View;

import Battle_System.Interface_Adapter.ViewModel;

public class Quiz_ViewModel extends ViewModel<Quiz_State> {

    public Quiz_ViewModel() {
        super("Quiz");
        setState(new Quiz_State());
    }
}

