package model;

public class Booking {
    private final String pnr;
    private final String passengerName;
    private final String trainNo;
    private final String trainName;
    private final String classType;
    private final String journeyDate;
    private final String source;
    private final String destination;

    public Booking(String pnr, String passengerName, String trainNo, String trainName,
                   String classType, String journeyDate, String source, String destination) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.source = source;
        this.destination = destination;
    }

    public String getPnr() { return pnr; }
    public String getPassengerName() { return passengerName; }
    public String getTrainNo() { return trainNo; }
    public String getTrainName() { return trainName; }
    public String getClassType() { return classType; }
    public String getJourneyDate() { return journeyDate; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
}
