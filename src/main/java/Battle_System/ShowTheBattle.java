package Battle_System;//package Battle_System;
//
//import Battle_System.DataAccess.*;
//import Battle_System.Entity.*;
//import Battle_System.GameAPI.*;
//import Battle_System.Interface_Adapter.Battle.*;
//import Battle_System.Interface_Adapter.ViewManagerModel;
//import Battle_System.UseCase.Battle.*;
//import Battle_System.View.Battle_View;
//
//import javax.swing.*;
//
//public class ShowTheBattle {
//
//    public static void main(String[] args) {
//        // 1. create DAO TODO: Not sure about this part need to be completed in the future.
//        BattleUserDataAccessInterface dao = new InMemoryBattleDataAccess();
//
//        // 2. create ViewModels
//        Battle_ViewModel battleViewModel = new Battle_ViewModel();
//        ViewManagerModel viewManagerModel = new ViewManagerModel("Battle");
//
//        // 3. create Presenter
//        Battle_Presenter presenter = new Battle_Presenter(battleViewModel, viewManagerModel);
//
//        // 4. create Interactor
//        Battle_Interactor interactor = new Battle_Interactor(dao, presenter);
//
//        // 5. create Controller
//        Battle_Controller controller = new Battle_Controller(interactor);
//
//        // 6. create Entities
//        User user = new User();
//        Monster monster = new Monster();
//        // Test message (Need to be Deleted)
//        // TODO: Delete This Message
//        System.out.println("User HP: " + user.getHP());
//        System.out.println("Monster HP: " + monster.getHP());
//
//        // 7. create View
//        Battle_View view = new Battle_View(battleViewModel,  viewManagerModel, );
//        view.setBattleController(controller);
//
//        // 8. Initialize the State
//        Battle_State state = battleViewModel.getState();
//        state.setUser(user);
//        state.setMonster(monster);
//
//        // TODO: Delete This Message, This is a Test Message
//        System.out.println("State User HP: " + state.getUserHp());
//        System.out.println("State Monster HP: " + state.getMonsterHP());
//        battleViewModel.firePropertyChange();
//
//
//        // TODO: Delete This Message, This is a Test Message
//        System.out.println("=== Battle Initialized ===");
//
//        // 9. Show the GUI
//        JFrame frame = new JFrame("Battle System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setContentPane(view);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
//        // TODO: Delete This Message, This is a Test Message
//        System.out.println("=== Window Displayed ===");
//    }
//}