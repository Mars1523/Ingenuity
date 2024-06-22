package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Turn extends Command {

    private Drivetrain drivetrain;
    private double rotation = 0;
    private double speed;
    private Direction direction;
    private boolean isFinished = false;

    enum Direction {
        Clockwise,
        CounterClockwise
    }

    public Turn(Drivetrain drivetrain, double speed, double rotation, Direction direction) {
        addRequirements(drivetrain);

        this.drivetrain = drivetrain;
        this.rotation = rotation;
        this.speed = speed;
        this.direction = direction;

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
        // System.out.println(drivetrain.getRotation() + " target: " + rotation);
        if (direction == Direction.CounterClockwise) {
            if (drivetrain.getRotation().getDegrees() < rotation) {
                drivetrain.driveMecanum(0, 0, speed);
            } else {
                drivetrain.driveMecanum(0, 0, 0);
                isFinished = true;
            }
        } else {
            if (drivetrain.getRotation().getDegrees() > -rotation) {
                drivetrain.driveMecanum(0, 0, -speed);
            } else {
                drivetrain.driveMecanum(0, 0, 0);
                isFinished = true;
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
        return isFinished;

    }
}