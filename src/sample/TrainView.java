package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class TrainView {
    TrainModel model;
    Controller control;
    private GridPane Startview;
    Button exitBtn =new Button("Exit");
    Button FindTrainsBtn = new Button("Find Trains");
    Label StartstationLbl = new Label("Select start Station:");
    Label EndstationLbl = new Label(" Select end station:");
    Label TimeLbl = new Label("Select earliest time:");
    Label TimesepLbl=new Label(":");
    TextArea TrainText = new TextArea();

    ComboBox<String> StartStationComB=new ComboBox<String>();
    ComboBox<String> EndStationComB=new ComboBox<String>();
    ComboBox<Integer> HourComB=new ComboBox<Integer>();
    ComboBox<Integer> MinutesComB=new ComboBox<Integer>();

    public TrainView(TrainModel model, Controller control){
        this.model=model;
        this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        Startview= new GridPane();
        Startview.setMinSize(300, 200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);

        Startview.add(StartstationLbl, 1,1);
        Startview.add(EndstationLbl,1,3);
        Startview.add(TimeLbl,1,5);
        Startview.add(FindTrainsBtn, 15, 6);
        Startview.add(TrainText, 1,7,15,7);
        Startview.add(exitBtn,20,15);
        ObservableList<String> stationList=control.getStations();
        StartStationComB.setItems(stationList);
        StartStationComB.getSelectionModel().selectFirst();
        Startview.add(StartStationComB,15,1);

        EndStationComB.setItems(stationList);
        EndStationComB.getSelectionModel().selectFirst();
        Startview.add(EndStationComB,15,3);

        HourComB.setItems(control.getHours());
        HourComB.getSelectionModel().selectFirst();
        Startview.add(HourComB, 13,5);
        Startview.add(TimesepLbl,14,5);

        MinutesComB.setItems(control.getMinutes());
        MinutesComB.getSelectionModel().selectFirst();
        Startview.add(MinutesComB,15,5);

    }
    public Parent asParent(){
        return Startview;
    }
}
