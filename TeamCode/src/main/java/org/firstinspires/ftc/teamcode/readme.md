10862 Center Stage Code
2023-2024
Robin
    Subsystems:
    Main Features:

ToDo:
Figure out Positions for the Vision Boxes
What color should the Team Prop be?
Make the Nebula Motor be able to flip the ENcoder Direction; motor.encoder.setDirection();
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
Commit Test

Ideas: