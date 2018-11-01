package org.usfirst.frc.team4087.robot.commands;

import org.usfirst.frc.team4087.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class WristDrive extends Command {

	public final double W_UpperLimit = -6000;
	public final double W_LowerLimit = 0;
	public double finalPosition;
	public double finalDirection;
	public double finalVelocity;

	public WristDrive() {
		requires(Robot.wrist);
	}

	protected void initialize() {

	}

	protected void execute() {

		// if (Math.abs(Robot.oi.getControlJoyYL()) > 0 &&
		// Robot.winch.getWinchPosition() >= W_UpperLimit &&
		// Robot.winch.getWinchPosition() <= W_LowerLimit) {
		if (Math.abs(Robot.oi.getControlJoyYR()) > 0 && Robot.wrist.getWristPosition() >= W_UpperLimit) {

			Robot.wrist.wristControl(ControlMode.PercentOutput, Robot.oi.getControlJoyYR() / 2);
			finalPosition = Robot.wrist.getWristPosition();
			finalDirection = Math.signum(Robot.oi.getControlJoyYR());
			finalVelocity = Robot.oi.getControlJoyYR();

		} else {

			Robot.wrist.wristControl(ControlMode.Position, finalPosition + 1200 * (finalVelocity));

		}

		if (finalPosition < W_UpperLimit) {
			finalPosition = W_UpperLimit;
		}

		/*
		 * if (finalPosition > W_LowerLimit) { finalPosition = W_LowerLimit; }
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