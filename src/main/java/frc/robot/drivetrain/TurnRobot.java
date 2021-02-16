package frc.robot.drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotConstants;

public class TurnRobot extends CommandBase{
    //error is how much we have to turn
    //gain is the speed to turn at based on error
    public double error;
    public double targetState;
    public PIDController turnRobot;
    public final double SPEED = 0.5; //TODO check value
    public int numLoops = 0;

    public TurnRobot(double degrees){
        super();
        System.out.println("TURN ROBOT IS CONSTUCTED");
        turnRobot = new PIDController(RobotConstants.TURN_KP, RobotConstants.TURN_KI, RobotConstants.TURN_KD);
        //TODO change the format of final constants to CAPS_AND_UNDERSCORES.
        targetState = degrees + Robot.navX.getAngle(); //Gets the abosolute position | TODO Find what get angle really means / gives back
        
    }
    
    @Override
    public void initialize() {
        super.initialize();
        turnRobot.setSetpoint(targetState);
        // turnRobot.setTolerance(3);//TODO Test Value and figure out units
        System.out.println("TURN ROBOT IS INITILIZED");
    }

    @Override
    public void execute() {
        turnRobot.calculate(Robot.navX.getAngle());
        //Robot.drivetrainsubsystem.arcadeDrive(SPEED, turnRobot.calculate(Robot.navX.getAngle()))
        if(++numLoops == 10){
            System.out.println("Current Angle: " + Robot.navX.getAngle());
            System.out.println("Target Angle: " + targetState);
            System.out.println("PID Output: " + turnRobot.calculate(Robot.navX.getAngle()));
            numLoops = 0;
        }
    }

    @Override
    public boolean isFinished() {
        if(turnRobot.getPositionError() < 3){ 
            System.out.println("Error is less than 3 units");
            return true;
        }
        
        return false;
    }

    @Override
    public void end(boolean interrupted){
        System.out.println("Robot should be stopped");
        // Robot.drivetrainsubsystem.arcadeDrive(0, 0);
    }
    
}