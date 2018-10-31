package org.usfirst.frc.team4087.robot.commands;

import org.usfirst.frc.team4087.robot.Robot;
import org.usfirst.frc.team4087.robot.subsystems.PID_Tuner;
import org.usfirst.frc.team4087.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch_PID extends Command {
	/*
	 * private double kP = Robot.pidtuner.P_Tuner(); private double kI =
	 * Robot.pidtuner.I_Tuner(); private double kD = Robot.pidtuner.D_Tuner();
	 */
	private double derivative;

	public double error;
	double integral, previous_error, setpoint = 0;

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public double PID() {
		Robot.pidtuner.PD_Tuner();

		double kP = Robot.pidtuner.P_Tuner();
		double kI = Robot.pidtuner.I_Tuner();
		double kD = Robot.pidtuner.D_Tuner();

//		double kP = 0.01;
//		double kI = 0;
//		double kD = .01;
		//double kD = 0.0000075;

		// SmartDashboard.putNumber("SP", setpoint);

		error = setpoint - Robot.winch.getWinchPosition();
		this.integral += (error * .02);

		derivative = (error - this.previous_error) / .02;

		return -(kP * error + kI * this.integral + kD * derivative);
	}

	public void execute() {
		PID();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
