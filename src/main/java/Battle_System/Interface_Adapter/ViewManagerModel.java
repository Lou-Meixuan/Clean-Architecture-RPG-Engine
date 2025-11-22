package Battle_System.Interface_Adapter;

public class ViewManagerModel extends ViewModel<String> {
    public  ViewManagerModel(String viewName) {
        super("View Manager");
        this.setState(viewName);
    }
}
