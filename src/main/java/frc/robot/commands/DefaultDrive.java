// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.auto.BalanceAuto;
import frc.robot.subsystems.Drivetrain;

public class DefaultDrive extends CommandBase {
    private XboxController primaryController;
    private Drivetrain drivetrain;
    private BalanceAuto balanceAuto;
    SlewRateLimiter filter = new SlewRateLimiter(1);
    SlewRateLimiter filterTurn = new SlewRateLimiter(1);

    // pretend that there is a second controller


    public DefaultDrive(Drivetrain drivetrain, XboxController primaryController) {
        addRequirements(drivetrain);
        this.primaryController = primaryController;
        this.drivetrain = drivetrain;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    //speed lowering toggle

    Boolean slow = false;
    SlewRateLimiter slowLimiter = new SlewRateLimiter(0.75);
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double xSpeed = -primaryController.getLeftY();
        if(primaryController.getAButtonPressed()) {
            slow = !slow;
        }

        //if , slow true x*=0.5
        //if , slow false x*=1
        xSpeed *= slowLimiter.calculate(slow ? 0.5 : 1);



        //toggle condition
        //if(speedDouble){
        //    xSpeed *= 0.5;
        //}

        final double rot = primaryController.getRightX();
        drivetrain.driveMecanum(
                xSpeed,
                primaryController.getLeftX() * 0,
                rot,
                true);
        if (primaryController.getStartButton()) {
            drivetrain.zero();
        }

        // drivetrain.driveMecanum(primaryController.getRightY() * .3,
        // primaryController.getLeftX() * .3,
        // primaryController.getRightX() * .3);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
