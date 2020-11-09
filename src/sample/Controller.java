package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller {
    TrainModel model;
    TrainView view;
    public Controller(TrainModel model){
        this.model=model;
        try{
            model.connect();
            model.CreateStatement();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<String> getStations(){
        ArrayList<String> Names= model.StationNameQuerystmt();
        ObservableList<String> StationNames = FXCollections.observableArrayList(Names);
        return StationNames;
    }
    public ObservableList<Integer> getHours(){
        ArrayList<Integer> Hours =new ArrayList<>();
        for(int i=0;i<24;i++){
            Hours.add(i);
        }
        ObservableList<Integer> HoursObs=FXCollections.observableArrayList(Hours);
        return HoursObs;
    }
    public  ObservableList<Integer> getMinutes(){
        ArrayList<Integer> Minutes= new ArrayList<>();
        for(int i=0;i<60;i++){
            Minutes.add(i);
        }
        ObservableList<Integer> MinutesObs=FXCollections.observableArrayList(Minutes);
        return MinutesObs;
    }
    public void setView(TrainView view){
        this.view=view;
        view.exitBtn.setOnAction(e-> Platform.exit());
        EventHandler<ActionEvent> PrintTrainRoutes = e->HandlePrintTrainRoutes(view.StartStationComB.getValue(), view.EndStationComB.getValue(),
                view.HourComB.getValue(), view.MinutesComB.getValue(), view.TrainText);
        view.FindTrainsBtn.setOnAction(PrintTrainRoutes);
    }
    public  void HandlePrintTrainRoutes(String From, String To, Integer hour,
                                        Integer minutes, TextArea TrainText){
          TrainText.clear();
          TrainText.appendText("Train, From station: Departure -> To station: arrival \n");
          model.preparedStmtToFromQuery();
          double time = (double) hour + ((double) minutes/100);
          ArrayList<TrainTrips> Trips=model.FindTrainTrips(From,To,time);
          for(int i=0;i<Trips.size();i++)
          {
              String deptime= String.format("%.2f", Trips.get(i).departure);
              String arrtime= String.format("%.2f", Trips.get(i).arrival);
              TrainText.appendText(Trips.get(i).TrainID +"; "+ Trips.get(i).From +
                      ": "+ deptime+ " -> "+ Trips.get(i).To +": "
                      +arrtime +"\n");
          }

    }
}
