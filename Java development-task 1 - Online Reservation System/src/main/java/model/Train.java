package model;

public class Train {
    private final String trainNo;
    private final String trainName;

    public Train(String trainNo, String trainName) {
        this.trainNo = trainNo;
        this.trainName = trainName;
    }

    public String getTrainNo() { return trainNo; }
    public String getTrainName() { return trainName; }
}
