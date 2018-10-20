/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4087.robot;

import org.usfirst.frc.team4087.robot.commands.WinchDrive;
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
		SmartDashboard.putNumber("Period", pidtuner.Period);
		SmartDashboard.putNumber("Winch Position", Robot.winch.getWinchPosition());
		SmartDashboard.putNumber("Winch Velocity", Robot.winch.getWinchVelocity());
		SmartDashboard.putNumber("P", pidtuner.P);
		SmartDashboard.putNumber("D", pidtuner.D);

		SmartDashboard.putNumber("Winch Setpoint", pidtuner.PID_Testing_Setpoint);
		SmartDashboard.putBoolean("Oscillating?", pidtuner.ifOscillating(Robot.winch.getWinchPosition(), pidtuner.PID_Testing_Setpoint));
		SmartDashboard.putNumber("Oscillation Time", System.currentTimeMillis() - pidtuner.startOscillationTime);

		SmartDashboard.putNumber("Oscillation Counter", pidtuner.OscillationCounter);
		SmartDashboard.putNumber("Previous Oscillation Counter", pidtuner.PrevOscillationCounter);
		SmartDashboard.putNumber("Position Counter", pidtuner.PositionCounter);
		SmartDashboard.putNumber("Previous Position Counter", pidtuner.PrevPositionCounter);
		
		SmartDashboard.putNumber("Final P", pidtuner.ResultantP);
		SmartDashboard.putNumber("Final I", pidtuner.I);
		SmartDashboard.putNumber("Final D", pidtuner.D);

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

		// SmartDashboard.putNumber("Period", 4);
		// SmartDashboard.putNumber("Oscillation Counter", pidtuner.OscillationCounter);

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
