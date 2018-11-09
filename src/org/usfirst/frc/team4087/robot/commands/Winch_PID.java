package org.usfirst.frc.team4087.robot.commands;

import org.usfirst.frc.team4087.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Winch_PID extends Command {

	private double derivative;

	public double error;
	double integral, previous_error, setpoint = 0;
	
	public double iterationTime = .02;

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public double PID() {
		Robot.pidtuner.Parameter_Tuner();

		double kP = Robot.pidtuner.P_Tuner();
		double kI = Robot.pidtuner.I_Tuner();
		double kD = Robot.pidtuner.D_Tuner();

		error = setpoint - Robot.winch.getWinchPosition();
		this.integral += (error * iterationTime);

		derivative = (error - this.previous_error) / iterationTime;

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
