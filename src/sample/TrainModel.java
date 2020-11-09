package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class TrainModel {
    Connection conn= null;
    Statement stmt =null;
    PreparedStatement pstmt=null;
    String url;
    public TrainModel(String url){
        this.url=url;
    }

    public void connect() throws SQLException {
        conn=getConnection(this.url);
    }
    public void CreateStatement() throws SQLException{
        this.stmt=conn.createStatement();
    }
    public ArrayList<String> StationNameQuerystmt(){
        ArrayList<String> StationNames=new ArrayList<String>();
        String sql="SELECT Name FROM station;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                String name=rs.getString(1);
                StationNames.add(name);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return StationNames;
    }
    public  void preparedStmtToFromQuery(){
        String sql=" SELECT D1.TrainID, D1.StationName, D1.Time, D2.StationName, D2.Time FROM Departure as D1 " +
                " JOIN Departure as D2 ON D1.TrainID= D2.TrainID " +
                "  WHERE D1.StationName = ? AND D2.StationName= ? AND D1.Time > ? AND D1.Time < D2.Time;";
        try {
            pstmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<TrainTrips> FindTrainTrips(String from, String to, double time){
        ArrayList<TrainTrips> Trips=new ArrayList<TrainTrips>();
        try {
            pstmt.setString(1, from);
            pstmt.setString(2, to);
            pstmt.setFloat(3, (float) time);
            ResultSet rs=pstmt.executeQuery();
            while (rs!=null && rs.next()){
                TrainTrips trip=new TrainTrips(rs.getInt(1),
                        rs.getString(2), rs.getFloat(3),
                        rs.getString(4),rs.getDouble(5));
                Trips.add(trip);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();

        }
        return Trips;
    }

}


class TrainTrips{
    Integer TrainID;
    String From;
    double departure;
    String To;
    double arrival;
    public TrainTrips(Integer TID,String from, double dep, String to, double arr ){
        this.arrival=arr;
        this.departure=dep;
        this.From=from;
        this.To = to;
        this.TrainID=TID;
    }
}