package org.usfirst.frc.team4087.robot.subsystems;

import org.usfirst.frc.team4087.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PID_Tuner extends Subsystem {

	public double CycleTime = 5;

	public double P = 0.0000001; //
	public double I = 0;
	public double D = 0;
	public double finalP = 0;
	public double previousP = 0;
	public double PPrime = 0;

	public double defaultSetpoint = 0; //
	public double testSetpoint = 16000; //
	public double currentPosition = 0;
	public double PID_Testing_Setpoint = 16000;
	public double currentMaxPosition = 0;
	public double CurrentOvershoot = 0;
	public double previousOvershoot = 0;

	public boolean PComplete = false;
	public boolean PTunerComplete2 = false;
	public boolean FirstTime = true;

	public long startCycleTime = System.currentTimeMillis();
	public long SystemTime = System.currentTimeMillis() - startCycleTime;

	public void setTimer0() {
		startCycleTime = System.currentTimeMillis();
	}

	public void Parameter_Tuner() {
		currentPosition = Robot.winch.getWinchPosition();
		SystemTime = (System.currentTimeMillis() - startCycleTime) / 1000;
		CurrentOvershoot = (currentMaxPosition - testSetpoint) / (testSetpoint - defaultSetpoint);
		PPrime = (CurrentOvershoot - previousOvershoot) / (P - previousP);
		if (!PComplete) {
			if (currentMaxPosition < testSetpoint) {
				if (SystemTime > CycleTime) {
					if (SystemTime < 2 * CycleTime) {
						PID_Testing_Setpoint = defaultSetpoint;
					} else {
						P *= 10;
						PID_Testing_Setpoint = testSetpoint;
						currentMaxPosition = 0;
						setTimer0();
					}
				} else {
					if (currentPosition > currentMaxPosition) {
						currentMaxPosition = currentPosition;
					}
				}
			} else {
				finalP = P;
				setTimer0();
				currentMaxPosition = 0;
				PComplete = true;
			}
		} else {
			if (!PTunerComplete2) {
				if (SystemTime > CycleTime) {
					if (currentMaxPosition < testSetpoint) {
						PTunerComplete2 = true;
					} else {
						if (SystemTime < 2 * CycleTime) {
							PID_Testing_Setpoint = defaultSetpoint;
						} else {
							P -= P * CurrentOvershoot;
							PID_Testing_Setpoint = testSetpoint;
							currentMaxPosition = 0;
							previousP = P;
							previousOvershoot = CurrentOvershoot;
							FirstTime = false;
							setTimer0();
						}
					}
				} else {
					if (currentPosition > currentMaxPosition) {
						currentMaxPosition = currentPosition;
					}
				}
			}
		}
	}

	public double P_Tuner() {
		return P;
	}

	public double I_Tuner() {
		return I;
	}

	public double D_Tuner() {
		return D;
	}

	@Override
	protected void initDefaultCommand() {
	}
}
//if (!FirstTime) {
//P -= P * CurrentOvershoot + PPrime * P;
//} else {