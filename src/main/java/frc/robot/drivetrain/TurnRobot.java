package frc.robot.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TurnRobot extends CommandBase{
    //error is how much we have to turn
    //gain is the speed to turn at based on error
    public double error;
    public double targetState;
    public double GAIN = 0.3; //TODO check value | Gain is the speed at which the robot will spin
    public double SPEED = 0.5; //TODO check value

    public TurnRobot(double degrees){
        super();
        targetState = degrees + Robot.navX.getAngle(); //Gets the abosolute position | TODO Find what get angle really means / gives back
        System.out.println(Robot.navX.getAngle());
        error = targetState - Robot.navX.getAngle(); //Gets the absolute error
        // Robot.drivetrainsubsystem.arcadeDrive(SPEED, error * GAIN);
    }
    
    @Override
    public void execute() {
        error = targetState - Robot.navX.getAngle();
        // System.out.println("Speed: " + SPEED);
        System.out.println("Speed we need to turn at: " + (error * GAIN));
        System.out.println("Current Angle: " + Robot.navX.getAngle());
        
        // Robot.drivetrainsubsystem.arcadeDrive(SPEED, error * GAIN);
    }

    @Override
    public boolean isFinished() {
        if(error < 5){ //TODO test value
            System.out.println("Error is < 5");
            return true;
        }
        
        System.out.println("Error is >= 5");
        return false;
    }

    @Override
    public void end(boolean interrupted){
        System.out.println("Robot should be stopped");
        // Robot.drivetrainsubsystem.arcadeDrive(0, 0);
    }
    
}