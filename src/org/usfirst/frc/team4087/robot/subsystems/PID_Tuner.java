package org.usfirst.frc.team4087.robot.subsystems;

import java.util.Random;

import org.usfirst.frc.team4087.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID_Tuner extends Subsystem {

	double MaxError = 0;

	double OscillationCounter = 0;
	double PrevOscillationCounter = 0;

	double PositionCounter = 0;
	double PrevPositionCounter = 0;

	double OscillationPeriodTimer = 0;

	double Period = 0;

	double P = 0.0000001;

	long startTime = 0;
	long endTime = 0;

	// added all this crap with P

	public double P_Tuner() {

		if (ifOscillating(Robot.winch.getWinchPosition(), 8000) == false && OscillationCounter <= 0) {

			return P *= 10;

		} else {

			return P;

		}

	}

	public double I_Tuner() {
		return 0;
	}

	public double D_Tuner() {
		return 0;
	}

	@Override
	protected void initDefaultCommand() {

	}

	public boolean ifOscillating(double CurrentPosition, double setpoint) {

		SmartDashboard.putNumber("Period", Period);
		SmartDashboard.putNumber("Oscillation Counter", OscillationCounter);
		SmartDashboard.putNumber("Winch Position", Robot.winch.getWinchPosition());

		if (CurrentPosition > setpoint) {

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

		if (OscillationCounter == 3) {

			startTime = System.currentTimeMillis();
		}

		if (OscillationCounter == 4) {

			endTime = System.currentTimeMillis();
		}
		// added thing here to reset the thing
		if (ifOscillating(CurrentPosition, setpoint) == false) {
			OscillationCounter = 0;
		}

		if (OscillationCounter > 2) {
			return true;
		} else {
			return false;
		}

	}

	public double returnPeriod(boolean ifOscillating) {

		Period = (endTime - startTime) / .02;
		return Period;

	}
}

/*
 * public int randomSetpoint() { Random rand = new Random(); int rand1 =
 * rand.nextInt(-35000); return rand1; }
 */
