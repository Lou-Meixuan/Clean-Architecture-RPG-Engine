package interface_adapter;

public class ViewManagerModel extends ViewModel<String> {
    public  ViewManagerModel(String viewName) {
        super("View Manager");
        this.setState(viewName);
    }
}
