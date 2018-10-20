package org.usfirst.frc.team4087.robot.commands;

import org.usfirst.frc.team4087.robot.Robot;
import org.usfirst.frc.team4087.robot.subsystems.Winch;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WinchDrive extends Command {

	public Winch_PID winch_pid = new Winch_PID();

	public final double W_UpperLimit = -34000;
	// public final double W_LowerLimit = 100;
	public double finalPosition;
	public double finalDirection;
	public double finalVelocity;
	public double aim, aim_previous = 0;
	public double vel, vel_previous = 0;

	public WinchDrive() {
		requires(Robot.winch);
	}

	protected void initialize() {

	}

	protected void execute() {


		//vel = 0; // vel_previous = 0;

		if (Robot.winch.getWinchPosition() >= W_UpperLimit) {

			aim += this.aim_previous + Robot.oi.getControlJoyYL() * 400;

		} else {
			aim = W_UpperLimit;
		}
		

		 winch_pid.setSetpoint(-aim);
		// Robot.winch.winchControl(ControlMode.PercentOutput, winch_pid.PID());

		// vel += this.vel_previous + Robot.oi.getControlJoyYL() * .2;

		// Robot.winch.winchControl(ControlMode.PercentOutput, -vel);

		winch_pid.setSetpoint(Robot.pidtuner.PID_Testing_Setpoint);
		
		Robot.winch.winchControl(ControlMode.PercentOutput, winch_pid.PID());
		// SmartDashboard.putNumber("",
		// winch_pid.pidtuner.ifOscillating(Robot.winch.getWinchPosition(),
		// winch_pid.setpoint));
	}

	@Override
	protected boolean isFinished() {

		return false;
	}

	protected void interrupted() {
		end();
	}

}