package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    private Drivetrain drivetrain;
    private double distance;
    private double speed;
    private boolean done = false;

    public Drive(Drivetrain drivetrain, double distance, double speed) {
        addRequirements(drivetrain);

        this.drivetrain = drivetrain;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        drivetrain.zeroSensors();
    }

    public double setDistance(double distance) {
        return distance;
    }

    @Override
    public void execute() {
        // if the robot has not reached the set distance, keep driving
        // otherwise, (if the distance has been reached), stop
        if (distance < 0) {
            if (drivetrain.getDistance() > distance) {
                drivetrain.driveMecanum(speed, 0, 0);
            } else {
                drivetrain.driveMecanum(0, 0, 0);
                done = true;
            }
        } else {
            if (drivetrain.getDistance() < distance) {
                drivetrain.driveMecanum(speed, 0, 0);
            } else {
                drivetrain.driveMecanum(0, 0, 0);
                done = true;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        // if the distance has been reached, the command is finished
        // otherwise, the command is not finished
        return done;
    }
}