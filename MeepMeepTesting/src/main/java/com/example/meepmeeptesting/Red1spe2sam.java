package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Red1spe2sam { public static void main(String[] args) {
	MeepMeep meepMeep = new MeepMeep(600);
	
	RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
		// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
		.setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
		.build();
	
	myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-16, -60, 300))
		//SPECIMEN DROP
		.splineToLinearHeading(new Pose2d(-12,-34,300),300)
		.waitSeconds(3)
		//INTAKE
		.splineToLinearHeading(new Pose2d(-34,-32,185.35),0)
		.strafeTo(new Vector2d(-34,-26))
		.waitSeconds(4)
		//SAMPLE1
		.splineToSplineHeading(new Pose2d(-54,-56,0.8),15)
		.waitSeconds(2)
		//INTAKE
		.splineToLinearHeading(new Pose2d(-39,-26,185.35),0)
		.waitSeconds(4)
		//SAMPLE2
		.splineToSplineHeading(new Pose2d(-54,-56,0.8),15)
		.waitSeconds(2)
		//PARK
		.splineToLinearHeading(new Pose2d(-26,-10,185.35),0)
		
		.build());
	
	meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
		.setDarkMode(true)
		.setBackgroundAlpha(0.95f)
		.addEntity(myBot)
		.start();
}
}

