package org.usfirst.frc.team4087.robot.subsystems;

import java.util.Random;

import org.usfirst.frc.team4087.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID_Tuner extends Subsystem {

	double MaxError = 0;

	public double OscillationCounter = 0;
	double PrevOscillationCounter = 0;

	double PositionCounter = 0;
	double PrevPositionCounter = 0;

	double OscillationPeriodTimer = 0;

	public double Period = 0;

	public double PID_Testing_Setpoint = 8000;
	public double TimerSwitch;

	public double P = 0.0000001;
	public long startOscillationTime = System.currentTimeMillis();
	double endOscillationTime = 0;
	double startPeriodTime = 0;
	double endPeriodTime = 0;

	public int iterationCounter = 0;

	// added all this crap with P
	public void setTimer0() {
		startOscillationTime = System.currentTimeMillis();

	}

	public double P_Tuner() {
		if (TimerSwitch == 0) {
			setTimer0();
			TimerSwitch++;
		}
		if (System.currentTimeMillis() - startOscillationTime > 5000) {

			if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == false) {
				PID_Testing_Setpoint = 0;

				TimerSwitch = 0;
				PID_Testing_Setpoint = 8000;
			}
			P *= 10;
			SmartDashboard.putString("Heyo?", "Over 5 seconds but no oscillation");

			return P;
		}

		if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == true) {
			PID_Testing_Setpoint = 0;

			if (System.currentTimeMillis() - startOscillationTime > 10000) {
				TimerSwitch = 0;
				PID_Testing_Setpoint = 8000;
			}
			SmartDashboard.putString("Done?", "All done!");
			return P;
		} else {

			if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == false) {
				SmartDashboard.putString("Heyo?", "Under 5 seconds but no oscillation");

				return P;
			} else {
				// TimerSwitch = 0;
				SmartDashboard.putString("Heyo?", "Under 5 seconds but oscillation");

				return P;
			}

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

			startPeriodTime = System.currentTimeMillis();
		}

		if (OscillationCounter == 4) {

			endPeriodTime = System.currentTimeMillis();
		}
		// added thing here to reset the thing

		Period = (endPeriodTime - startPeriodTime) / .02;

		if (Period < 1 && Period != 0) {
			return true;
		} else {
			return false;
		}

	}

	public double returnPeriod(boolean ifOscillating) {

		Period = (endPeriodTime - startPeriodTime) / .02;
		return Period;

	}
}

/*
 * public int randomSetpoint() { Random rand = new Random(); int rand1 =
 * rand.nextInt(-35000); return rand1; }
 */
