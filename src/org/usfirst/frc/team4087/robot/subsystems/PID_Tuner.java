package org.usfirst.frc.team4087.robot.subsystems;

import java.util.Random;

import org.usfirst.frc.team4087.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID_Tuner extends Subsystem {

	public boolean PComplete = false;
	public boolean PTunerComplete2 = false;
	public boolean DComplete = false;

	public double defaultPosition = 0;
	public double testPosition = 16000;
	public double currentPosition = 0;
	public double CurrentMaxPosition = 0;

	public double OscillationCounter = 0;
	public double PrevOscillationCounter = 0;

	public double PositionCounter = 0;
	public double PrevPositionCounter = 0;

	public boolean PreviousOscillating = true;
	public boolean CurrentOscillating = false;

	public double Period = 1;
	public double CurrentAvgSpeed = 0;
	public double PreviousAvgSpeed = 0;

	public double PID_Testing_Setpoint = 16000;
	public double finalP = 0;
	public double finalI = 0;
	public double finalD = 0;

	public double P = 0.0001;
	public double I = 0;
	public double D = 0;

	public double previousOscillationCounter = 0;

	public boolean FirstTime = true;

	public long startOscillationTime = System.currentTimeMillis();
	double startPeriodTime = 0;
	double endPeriodTime = 0;

	public int iterationCounter = 0;
	public double SystemTime = System.currentTimeMillis() - startOscillationTime;

	// added all this crap with P
	public void setTimer0() {
		startOscillationTime = System.currentTimeMillis();
	}

	public void PD_Tuner() {
		currentPosition = Robot.winch.getWinchPosition();
		SystemTime = System.currentTimeMillis() - startOscillationTime;
		
		if (!PComplete) {
			if (CurrentMaxPosition < testPosition) {

				if (SystemTime > 5000) {
					if (SystemTime < 10000) {
						PID_Testing_Setpoint = 0;
					} else {
						PID_Testing_Setpoint = 16000;
						P *= 10;
						PositionCounter = 0;
						PrevPositionCounter = 0;
						setTimer0();
					}
				} else {
					if (currentPosition > CurrentMaxPosition) {
						CurrentMaxPosition = currentPosition;
					}
					if (currentPosition > testPosition) {
						PositionCounter++;
					}
				}
			} else {

				finalP = P;
				previousOscillationCounter = OscillationCounter;
				PComplete = true;
				CurrentOscillating = true;
			}

		} else {
			if (!PTunerComplete2) {
				if (SystemTime > 5) {
					if (PositionCounter - PrevPositionCounter == 0) {
						PTunerComplete2 = true;
					} else {
						if (SystemTime > 10) {
							P -= P * (CurrentMaxPosition) / (testPosition - defaultPosition);
							CurrentMaxPosition = 0;
							PrevPositionCounter = PositionCounter;
							PID_Testing_Setpoint = defaultPosition;
						} else {
							PID_Testing_Setpoint = defaultPosition;
							setTimer0();
						}
					}
				} else {
					if (currentPosition > CurrentMaxPosition) {
						CurrentMaxPosition = currentPosition;
					}
					if (currentPosition > testPosition) {
						PositionCounter++;
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

	public boolean ifOscillatingD(double CurrentPosition, double setpoint, double AvgSpeed) {

		boolean ifOscillatingBoolean = true;

		if (PID_Testing_Setpoint != 0) {
			if (CurrentPosition > PID_Testing_Setpoint) {

				PositionCounter++;

			} else {

				PrevPositionCounter = PositionCounter;

				if (PrevOscillationCounter == OscillationCounter - 1) {

					PrevOscillationCounter++;
				}
			}

			if (PositionCounter > PrevPositionCounter && OscillationCounter == PrevOscillationCounter) {
				OscillationCounter++;
			}

			if (AvgSpeed > 10) {

				if (OscillationCounter - previousOscillationCounter >= 6) {
					ifOscillatingBoolean = true;
				} else {
					ifOscillatingBoolean = false;
				}
			} else {
				ifOscillatingBoolean = false;
			}

		} else {

			ifOscillatingBoolean = false;
		}
		return ifOscillatingBoolean;
	}

	public boolean ifOscillatingP(double CurrentPosition, double setpoint) {

		boolean ifOscillatingBoolean = true;

		if (PID_Testing_Setpoint != 0) {
			if (CurrentPosition > PID_Testing_Setpoint) {

				PositionCounter++;

			} else {

				PrevPositionCounter = PositionCounter;

				if (PrevOscillationCounter == OscillationCounter - 1) {

					PrevOscillationCounter++;
				}
			}

			if (PositionCounter > PrevPositionCounter && OscillationCounter == PrevOscillationCounter) {
				OscillationCounter++;
			}

			if (OscillationCounter - previousOscillationCounter >= 6) {
				ifOscillatingBoolean = true;
			} else {
				ifOscillatingBoolean = false;
			}

		} else {

			ifOscillatingBoolean = false;
		}
		return ifOscillatingBoolean;
	}

	public double returnPeriod() {

		Period = (endPeriodTime - startPeriodTime) / .02;
		return Period;

	}
}

/*
 * public int randomSetpoint() { Random rand = new Random(); int rand1 =
 * rand.nextInt(-35000); return rand1; }
 */
