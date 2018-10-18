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

	double CP;
	double SP;

	long startTime = 0;
	long endTime = 0;

	public double P_Tuner() {

		return .002;
		// .0001
	}

	public double I_Tuner() {
		return 0;
	}

	public double D_Tuner() {
		return 0;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public double ifOscillating(double CurrentPosition, double setpoint) {

		SmartDashboard.putNumber("Period", endTime - startTime);
		SmartDashboard.putNumber("Oscillation Counter", OscillationCounter);
		SmartDashboard.putNumber("Previous Oscillation Counter", PrevOscillationCounter);
		SmartDashboard.putNumber("Winch Position", Robot.winch.getWinchPosition());

		if (CurrentPosition > setpoint) {

			PositionCounter++;

		} else {

			PrevPositionCounter = PositionCounter;

			if (PrevOscillationCounter == OscillationCounter - 1) {

				PrevOscillationCounter++;
			}
		}
		if (OscillationCounter == 2) {

			startTime = System.currentTimeMillis();
		}

		if (OscillationCounter == 3) {

			endTime = System.currentTimeMillis();
		}
		return setpoint;
	}
}

/*
 * public int randomSetpoint() { Random rand = new Random(); int rand1 =
 * rand.nextInt(-35000); return rand1; }
 */

/*
 * See NI Software link -- Maybe a better way to measure oscillation would be to
 * transfer the range of values into a sigmoid or some other function that makes
 * it binary or close to binary, to trigger within a certain range, then measure
 * that triggering? Kinda similar to how we do it now but different at the same
 * time I'd suggest look at the NI link I sent to you, there's some good stuff
 * there about measuring the oscillation, although they use a frequency rather
 * than a physical oscillation which is slightly different in principle We also
 * should probably think about graphing the motion of the elevator over time,
 * which might help us figure out exactly what the sensor is measuring and
 * classifying as "oscillation" That's a big one, graphing it -- fairly easy if
 * we find the right libraries and run the program through the correct processor
 */