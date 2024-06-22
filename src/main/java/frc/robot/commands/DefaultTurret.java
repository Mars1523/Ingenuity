// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.subsystems.ClawSystem;
import frc.robot.commands.limelight.LimelightHorizAim;

public class DefaultTurret extends Command {
    // Constant variables for speed
    private final double armExtendSpeed = .6;
    private final double TableSpinSpeed = .3;
    private final double armMoveSpeed = .2;

    private XboxController primaryController;
    private XboxController secondaryController;
    private ClawSystem clawSystem;
    private boolean claw = false;

    /** Creates a new DefaultTurret. */
    public DefaultTurret(ClawSystem clawSystem, XboxController primaryController, XboxController secondaryController) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(clawSystem);
        this.primaryController = primaryController;
        this.secondaryController = secondaryController;
        this.clawSystem = clawSystem;

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // code for extending arm
       // if (primaryController.getAButton()) {
       //     clawSystem.setExtendSetPoint(armExtendSpeed);
       // } else if (primaryController.getBButton()) {
       //     clawSystem.setExtendSetPoint(-armExtendSpeed);
       // }

        // code for claw
        if (secondaryController.getAButtonPressed()) {

            if (claw == true) {
                clawSystem.openClaw();
                claw = !claw;
            } else if (claw == false) {
                clawSystem.closeClaw();
                claw = !claw;
            }
        }
        // old code for the turntable
        clawSystem.spinTable(secondaryController.getLeftX() / 2);
        // setArmSetPoint(secondaryController.getLeftX() * 1 / 50);
        // setArmSetPoint(set);

        // evil TT code
        // clawSystem.spinTablePID((MathUtil.applyDeadband(secondaryController.getLeftX(),
        // .05) * 15)
        // + clawSystem.getSpinTableSetpoint());

        // extending arm on second controller
        // if (secondaryController.getRightBumper()) {
        // extendSet = MathUtil.clamp(extendSet + 0.5, 0, 45);
        // clawSystem.setExtendSetPoint(extendSet);
        // } else if (secondaryController.getLeftBumper()){
        // extendSet = MathUtil.clamp(extendSet - 0.5, 0, 45);
        // clawSystem.setExtendSetPoint(extendSet);
        // }
        // System.out.println(extendSet);
        // clawSystem.setExtendSetPoint(extendSet);

        // else {
        // clawSystem.setExtendSetPoint(extendSet);
        // }

        // //sets setpoint for PID
        // if(secondaryController.getXButton()){
        // set = set + 0.1;
        // }
        // if(secondaryController.getYButton()){
        // set = set - 0.1;
        // }
        // secondarycontroller > 0.1 thing

        // our own very special deadband method!!!
        double extendController;
        if (secondaryController.getRightY() < 0.03 && secondaryController.getRightY() > -0.03) {
            extendController = 0;
        } else {
            extendController = secondaryController.getRightY() * 2;
        }

        double moveController;
        if (secondaryController.getLeftY() < 0.03 && secondaryController.getLeftY() > -0.03) {
            moveController = 0;
        } else {
            moveController = secondaryController.getLeftY() * 10;
        }

        if (secondaryController.getXButton()) {
            clawSystem.setGrabPoint();
        }

        var extendSet = -extendController + -clawSystem.getExtendSetPoint();
        clawSystem.setExtendSetPoint(extendSet);

        var armSetpoint = moveController + clawSystem.getArmSetPoint();
        clawSystem.setArmSetPoint(armSetpoint);
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
