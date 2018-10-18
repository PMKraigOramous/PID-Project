package org.usfirst.frc.team4087.robot.commands;

import org.usfirst.frc.team4087.robot.Robot;
import org.usfirst.frc.team4087.robot.subsystems.PID_Tuner;
import org.usfirst.frc.team4087.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.command.Command;

public class Winch_PID extends Command {

	PID_Tuner pidtuner = new PID_Tuner();
	private double kP = pidtuner.P_Tuner();
	private double kI = pidtuner.I_Tuner();
	private double kD = pidtuner.D_Tuner();

	private double derivative;
	private double WinchMotorPower;

	public double error;
	double integral, previous_error, setpoint = 0;

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	public double PID() {
		error = setpoint - Robot.winch.getWinchPosition();
		this.integral += (error * .02);

		derivative = (error - this.previous_error) / .02;
		return WinchMotorPower = -(kP * error + kI * this.integral + kD * derivative);
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
