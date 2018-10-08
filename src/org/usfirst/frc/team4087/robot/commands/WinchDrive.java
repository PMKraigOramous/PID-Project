package org.usfirst.frc.team4087.robot.commands;

import org.usfirst.frc.team4087.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class WinchDrive extends Command {

	public final double W_UpperLimit = -34000;
	//public final double W_LowerLimit = 100;
	public double finalPosition;

	public WinchDrive() {
		requires(Robot.winch);
	}

	protected void initialize() {

	}

	protected void execute() {

		//if (Math.abs(Robot.oi.getControlJoyYL()) > 0 && Robot.winch.getWinchPosition() >= W_UpperLimit &&  Robot.winch.getWinchPosition() <= W_LowerLimit) {
		if (Math.abs(Robot.oi.getControlJoyYL()) > 0 && Robot.winch.getWinchPosition() >= W_UpperLimit) {

			Robot.winch.winchControl(ControlMode.PercentOutput, Robot.oi.getControlJoyYL());
			finalPosition = Robot.winch.getWinchPosition();

		} else {

			Robot.winch.winchControl(ControlMode.Position, finalPosition);

		}
	

		if (finalPosition < W_UpperLimit) {
			finalPosition = W_UpperLimit;
		}
		
		/*
		if (finalPosition > W_LowerLimit) {
			finalPosition = W_LowerLimit;
		}
		*/
	}

	@Override
	protected boolean isFinished() {

		return false;
	}

	protected void interrupted() {
		end();
	}

}