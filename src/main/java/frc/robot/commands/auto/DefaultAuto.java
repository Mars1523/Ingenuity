package frc.robot.commands.auto;

import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.auto.Drive;
import frc.robot.subsystems.ClawSystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DefaultAuto extends Command {
    Drivetrain drivetrain;
    ClawSystem clawSystem;

    public DefaultAuto(Drivetrain drivetrain, ClawSystem clawSystem) {

        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
        this.clawSystem = clawSystem;
    }

    @Override
    public void initialize() {
        /* nothing */}

    @Override
    public void execute() {

        new SequentialCommandGroup(
                new Drive(drivetrain, 0.1, 0.1)).schedule();

        // proposed command structure:

        /*
         * new SequentialCommandGroup(
         * new AlignPole(clawSystem).withTimeout(0.1),
         * new Reach(clawSystem, +speed).withTimeout(time),
         * new Lower(clawSystem),
         * new OpenClaw(clawSystem),
         * new Raise(clawSystem),
         * new Reach(clawSystem, -speed).withTimeout(time),
         * new DriveBackwards(drivetrain, distance),
         * new Align(no idea how to do this one)
         * ).schedule();
         */

    }

    @Override
    public void end(boolean interrupted) {
        /* nothing */}

    @Override
    public boolean isFinished() {
        return false;
    }
}