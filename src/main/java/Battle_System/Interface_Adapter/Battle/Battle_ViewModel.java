package Battle_System.Interface_Adapter.Battle;

import Battle_System.Interface_Adapter.ViewModel;

public class Battle_ViewModel extends ViewModel<Battle_State> {

    public Battle_ViewModel() {
        super("Battle");
        setState(new Battle_State());
    }
}
