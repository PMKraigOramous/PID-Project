package org.usfirst.frc.team4087.robot.subsystems;

import java.util.Random;

import org.usfirst.frc.team4087.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID_Tuner extends Subsystem {

	double MaxError = 0;
	public boolean PComplete = false;
	public boolean DComplete = false;

	public double OscillationCounter = 0;
	public double PrevOscillationCounter = 0;

	public double PositionCounter = 0;
	public double PrevPositionCounter = 0;

	double OscillationPeriodTimer = 0;

	public double Period = 1;

	public double PID_Testing_Setpoint = 16000;
	public double TimerSwitch;
	public double finalP = 0;
	public double finalD = 0;

	public double P = 0.0000001;
	public double I = 0;
	public double D = 0;

	public double ResultantP = 0;

	public long startOscillationTime = System.currentTimeMillis();
	double endOscillationTime = 0;
	double startPeriodTime = 0;
	double endPeriodTime = 0;

	public int iterationCounter = 0;

	// added all this crap with P
	public void setTimer0() {
		startOscillationTime = System.currentTimeMillis();

	}

	public void PD_Tuner() {
		if (!PComplete) {
			if (System.currentTimeMillis() - startOscillationTime > 5000) {

				if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == false) {
					if (System.currentTimeMillis() - startOscillationTime < 10000) {
						PID_Testing_Setpoint = 0;
					} else {
						PID_Testing_Setpoint = 16000;
						setTimer0();
						P *= 10;
						OscillationCounter = 0;
						PrevOscillationCounter = 0;
					}
					SmartDashboard.putString("Heyo?", "Over 5 seconds but no oscillation");
				}

				if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == true) {
					PID_Testing_Setpoint = 0;
					OscillationCounter = 0;
					PrevOscillationCounter = 0;
					PComplete = true;
					finalP = P;
					P = P / 10;
					D = .0000000001;
					setTimer0();
					SmartDashboard.putString("Heyo?", "Done");
				}
			} else {

				if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == false) {
					SmartDashboard.putString("Heyo?", "Under 5 seconds but no oscillation");

				} else {
					// TimerSwitch = 0;
					PID_Testing_Setpoint = 0;
					OscillationCounter = 0;
					PrevOscillationCounter = 0;
					finalP = P;
					P = P / 10;
					SmartDashboard.putString("Heyo?", "Done");
					D = .0000000001;
					setTimer0();
					PComplete = true;

				}

			}

		} else {

			if (!DComplete) {
				if (System.currentTimeMillis() - startOscillationTime > 5000) {

					if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == true) {
						if (System.currentTimeMillis() - startOscillationTime < 10000) {
							PID_Testing_Setpoint = 0;
						} else {
							PID_Testing_Setpoint = 16000;
							setTimer0();
							D *= 2;
							OscillationCounter = 0;
						}
						SmartDashboard.putString("Heyo?", "Over 5 seconds but no oscillation");
					}

					if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == false) {
						PID_Testing_Setpoint = 0;
						OscillationCounter = 0;
						PComplete = true;
						finalP = P;
						finalD = D;
						P = 0;
						D = 0;
						SmartDashboard.putString("Heyo?", "Done");
					}
				} else {

					if (ifOscillating(Robot.winch.getWinchPosition(), PID_Testing_Setpoint) == true) {
						SmartDashboard.putString("Heyo?", "Under 5 seconds but no oscillation");

					} else {
						// TimerSwitch = 0;
						PID_Testing_Setpoint = 0;
						OscillationCounter = 0;
						finalD = D;
						SmartDashboard.putString("Heyo?", "Done");
						DComplete = true;

					}

				}
			}
		}
	}

	public double P_Tuner() {

		return P;

	}

	public double I_Tuner() {

		return 0;
	}

	public double D_Tuner() {

		return D;
	}

	@Override
	protected void initDefaultCommand() {

	}

	public boolean ifOscillating(double CurrentPosition, double setpoint) {
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

			if (OscillationCounter == 6) {

				startPeriodTime = System.currentTimeMillis();
			}

			if (OscillationCounter == 7) {

				endPeriodTime = System.currentTimeMillis();
			}
			// added thing here to reset the thing

			Period = (endPeriodTime - startPeriodTime) / .02;

			if (Period < 100000 && Period > .01 && OscillationCounter >= 6) {
				return true;
			} else {
				return false;
			}
		} else {
			OscillationCounter = 0;
			PrevOscillationCounter = 0;
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
