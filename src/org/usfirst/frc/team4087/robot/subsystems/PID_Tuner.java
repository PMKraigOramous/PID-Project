package org.usfirst.frc.team4087.robot.subsystems;

import org.usfirst.frc.team4087.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PID_Tuner extends Subsystem {

	// User Input:
	public double CycleTime = 10;
	public double P = 0.0001;
	public double defaultSetpoint = 0;
	public double testSetpoint = 16000;

	// Algorithm-generated PID info
	public double I = 0;
	public double D = 0;
	public double finalP = 0;
	public double previousP = 0;
	public double PPrime = 0;

	// Algorithm-generated position and setpoint info
	public double currentPosition = 0;
	public double PID_Testing_Setpoint = 16000;
	public double currentMaxPosition = 0;
	public double currentMaxOvershoot = 0;
	public double previousMaxOvershoot = 0;
	public double currentFinalPosition = 0;
	public double currentFinalOvershoot = 0;

	// Algorithm-generated completion statuses
	public boolean PComplete = false;
	public boolean PComplete2 = false;
	public boolean IComplete = false;
	public boolean IComplete2 = false;
	public boolean FirstTime = true;

	// Timing system
	public long startCycleTime = System.currentTimeMillis();
	public long SystemTime = System.currentTimeMillis() - startCycleTime;

	public void resetTimer() {
		startCycleTime = System.currentTimeMillis();
	}

	// Tuning function for the P, I, and D values
	public void Parameter_Tuner() {

		// Variables updated every iteration
		currentPosition = Robot.winch.getWinchPosition();
		SystemTime = (System.currentTimeMillis() - startCycleTime) / 1000;
		currentMaxOvershoot = (currentMaxPosition - testSetpoint) / (testSetpoint - defaultSetpoint);
		currentFinalOvershoot = (currentFinalPosition - testSetpoint) / (testSetpoint - defaultSetpoint);
		PPrime = (currentMaxOvershoot - previousMaxOvershoot) / (P - previousP);

		// Rough P tuner
		if (!PComplete) {
			if (currentMaxPosition < testSetpoint) {
				if (SystemTime > CycleTime / 2) {
					if (SystemTime < CycleTime) {
						PID_Testing_Setpoint = defaultSetpoint;
					} else {
						P *= 10;
						PID_Testing_Setpoint = testSetpoint;
						currentMaxPosition = 0;
						resetTimer();
					}
				} else {
					if (currentPosition > currentMaxPosition) {
						currentMaxPosition = currentPosition;
					}
				}
			} else {
				finalP = P;
				resetTimer();
				currentMaxPosition = 0;
				PComplete = true;
			}
		} else {

			// Precise P tuner
			if (!PComplete2) {
				if (SystemTime > CycleTime / 2) {
					if (currentMaxPosition < testSetpoint) {
						I = P / 100000;
						PComplete2 = true;
					} else {
						if (SystemTime < CycleTime) {
							PID_Testing_Setpoint = defaultSetpoint;
						} else {
							P -= P * currentMaxOvershoot;
							PID_Testing_Setpoint = testSetpoint;
							currentMaxPosition = 0;
							previousP = P;
							previousMaxOvershoot = currentMaxOvershoot;
							FirstTime = false;
							resetTimer();
						}
					}
				} else {
					if (currentPosition > currentMaxPosition) {
						currentMaxPosition = currentPosition;
					}
				}
			} else {

				// Rough I tuner
				if (!IComplete) {
					if (SystemTime > CycleTime / 2) {
						if (currentFinalPosition >= testSetpoint) {
							IComplete = true;
						} else {
							if (SystemTime < CycleTime) {
								PID_Testing_Setpoint = defaultSetpoint;
							} else {
								I *= 10;
								PID_Testing_Setpoint = testSetpoint;
								currentFinalPosition = 0;
								resetTimer();
							}
						}
					} else {
						currentFinalPosition = currentPosition;
					}
				} else {

					// Precise I
					if (!IComplete2) {
						if (SystemTime > CycleTime / 2) {
							if (currentFinalPosition <= testSetpoint) {
								IComplete2 = true;
							} else {
								if (SystemTime < CycleTime) {
									PID_Testing_Setpoint = defaultSetpoint;
								} else {
									I -= I * currentFinalOvershoot;
									PID_Testing_Setpoint = testSetpoint;
									currentFinalPosition = 0;
									resetTimer();
								}
							}
						} else {
							currentFinalPosition = currentPosition;
						}
					}
				}
			}
		}
	}

	// Functions meant only to return the corresponding P, I, and D values
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