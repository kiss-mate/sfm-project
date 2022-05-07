package models;

public class MainViewDto {
    private MainViewModel mainViewModel;
    private String actionSource;

    public MainViewDto(MainViewModel mainViewModel, String actionSource) {
        this.mainViewModel = mainViewModel;
        this.actionSource = actionSource;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public String getActionSource() {
        return actionSource;
    }

    @Override
    public String toString() {
        return "MainViewDto{" +
                "mainViewModel=" + mainViewModel +
                ", actionSource='" + actionSource + '\'' +
                '}';
    }
}
