10862 Into The Deep Code
2024-2025
Timmy (Thomas)
    Subsystems: Mec Drive, Intake with extension, and Outtake with slides and arm
    Main Features:

ToDo:
Checking if sensors work
    public double check(){
        readValue = defaultOutput;
        if(on){
            double startTime = System.currentTimeMillis();
            double temp = sensor.getDistance(DistanceUnit.INCH);
            if(System.currentTimeMillis()-startTime > cutOff){
                if(brokeCount++ > 5) ;  on = false;
            }else{
                readValue = temp;
            }
        }
        return readValue;
    }
     for(String s : bot.claw.brokenSensors()){
            telemetry.addData("Broken: ", s);



