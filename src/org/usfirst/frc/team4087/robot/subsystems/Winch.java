package org.usfirst.frc.team4087.robot.subsystems;

import org.usfirst.frc.team4087.robot.Robot;
import org.usfirst.frc.team4087.robot.RobotMap;
import org.usfirst.frc.team4087.robot.commands.WinchDrive;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Winch extends Subsystem {
	
	private TalonSRX W_Master;
	private TalonSRX W_Slave;
	
	
	private final int timeoutMS = 10;
	
	public final double W_UpperLimit = 35000;
	public final double W_LowerLimit = 0;

	
	public Winch() {
		
		W_Master = new TalonSRX(RobotMap.WINCH_MASTER.value);
		W_Slave = new TalonSRX(RobotMap.WINCH_SLAVE.value);
		
		Robot.initTalon(W_Master);
		Robot.initTalon(W_Slave);		
		
		W_Slave.follow(W_Master);

		
	}
	
	public void winchControl(ControlMode mode, double target) {
		
		W_Master.set(mode, target);

 	}
	
	public double getWinchPosition() {
		
		return W_Master.getSelectedSensorPosition();
		
	}
	
	public double getWinchVelocity() {
		
		return W_Master.getSelectedSensorVelocity();
		
	}
	
	

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new WinchDrive());
		
	}

}