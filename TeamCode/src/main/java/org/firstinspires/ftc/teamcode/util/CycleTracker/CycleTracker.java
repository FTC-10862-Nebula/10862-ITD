package org.firstinspires.ftc.teamcode.util.CycleTracker;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.StatCalculator;

import java.util.HashMap;

//Make sure to write the time
public class CycleTracker {//TODO:Remove Static
    protected StatCalculator stat = new StatCalculator();
    protected ElapsedTime timer = new ElapsedTime(0);
    private final Telemetry tl;

    private HashMap<String, Double> data;
    private double cycle =0,
        mean = 0,
        fastest = 0,
        slowest = 0,
        score = 0,
        elapsedTime = 0;

    public CycleTracker(Telemetry tl) {
        timer.reset();
        this.tl = tl;
    }
    public void resetTimer(){
        timer.reset();
    }




    public void trackCycle(){
        double cycleTime = timer.seconds();
        stat.addNumber(cycleTime);
        cycle = stat.getSizeDouble();
        mean = stat.getMean();
        slowest = stat.getHighestValue();
        fastest = stat.getLowestValue();
        elapsedTime = stat.getSum();
        // switch (getPosition()){
        //     case HIGH:
        //         high.set(high.get()+1);
        //         break;
        //     case LOW:
        //         low.set(low.get()+1);
        //         break;
        score+=1;
        timer.reset();
    }

//    public  void printOut(String string, double num){
//        data.put(string, num);
//        data.put(String.valueOf(LocalTime.now()), 0);//TODO:TIME
//    }

//    public  void printAllData(){
////        printOut(DriverStation.getMatchType().toString(), DriverStation.getMatchNumber());
//
//        for (int i = 0; i < stat.getSizeInt(); i++) {
//            printOutStream("Cycle "+ i +":", stat.getNum(i));
//        }
//        printOut("High: ", high);
//        printOut("Low: ", low);
//        printOut("Total Cycles: ", cycle);
//        printOut("Mean", mean);
//        printOut("Fastest: ", fastest);
//        printOut("SLowest", slowest);
////        printOut("Fastest: ", stat.getHighestValue());
////        printOut("SLowest", stat.getLowestValue());
//    }
//    public  void printOutStream(String string, double num){
//        stream.print(string + num);
////        data.put(string, num);
//    }
    public void cyclePeriodic(){
        tl.addData("Elapsed Time", elapsedTime);
        tl.addData("Mean", mean);
        tl.addData("Cycle", cycle);

        tl.addData("Score", score);

        tl.addData("Fast", fastest);
        tl.addData("Slow", slowest);
    }
}
