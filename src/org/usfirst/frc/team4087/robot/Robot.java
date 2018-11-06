/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4087.robot;

import org.usfirst.frc.team4087.robot.subsystems.Drivebase;
import org.usfirst.frc.team4087.robot.subsystems.PID_Tuner;
import org.usfirst.frc.team4087.robot.subsystems.Winch;
import org.usfirst.frc.team4087.robot.subsystems.Wrist;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static OI oi;
	public static Drivebase drivebase;
	public static Winch winch;
	public static Wrist wrist;
	public static PID_Tuner pidtuner;

	@Override
	public void robotInit() {
		oi = new OI();
		drivebase = new Drivebase();
		winch = new Winch();
		wrist = new Wrist();
		pidtuner = new PID_Tuner();
	}

	@Override
	public void robotPeriodic() {

		// Oscillation Measurements
		SmartDashboard.putNumber("Cycle Time", pidtuner.SystemTime*10);

		// Final PID Values
		SmartDashboard.putNumber("Final P", pidtuner.finalP);

		// Current PID Values
		SmartDashboard.putNumber("Current P", pidtuner.P);

		// Winch Measurements
		SmartDashboard.putNumber("Winch Position (Graph)", Robot.winch.getWinchPosition());
		SmartDashboard.putNumber("Winch Position (Label)", Robot.winch.getWinchPosition());
		SmartDashboard.putNumber("Winch Velocity", Robot.winch.getWinchVelocity());
		SmartDashboard.putNumber("Winch Setpoint", pidtuner.PID_Testing_Setpoint);
		SmartDashboard.putNumber("Cycle Max Position", pidtuner.currentMaxPosition);

		// PID Completion Status
		SmartDashboard.putBoolean("P Complete?", pidtuner.PComplete);
		SmartDashboard.putBoolean("P 2 Complete?", pidtuner.PTunerComplete2);

	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}

	public static void initTalon(TalonSRX motor) {
		motor.setNeutralMode(NeutralMode.Brake);
		motor.neutralOutput();
		motor.setSensorPhase(false);
		motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
		motor.configNominalOutputForward(0.0, 0);
		motor.configNominalOutputReverse(0.0, 0);

	}

}
