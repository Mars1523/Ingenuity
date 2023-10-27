// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.util.sendable.*;
import frc.robot.commands.auto.Drive;
import frc.robot.commands.auto.ChangeClawStatus.ClawStatus;
import frc.robot.commands.auto.Turn.Direction;
import frc.robot.subsystems.ClawSystem;
import frc.robot.subsystems.Drivetrain;

public class FinalAuto extends SequentialCommandGroup {
    /** Creates a new FinalAuto. */
    Drivetrain drivetrain;
    ClawSystem clawSystem;
    Drive drive;

    public FinalAuto(Drive drive, Drivetrain drivetrain, ClawSystem clawSystem) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain);
        addRequirements(clawSystem);
        this.drivetrain = drivetrain;
        this.clawSystem = clawSystem;
        this.drive = drive;

        // int mode = 1;
    }

    /*
     * public void edgeMidGoal() {
     * // if (mode == 1)
     * 
     * // {
     * // Mode 1 for going to another cone after
     * addCommands(
     * new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
     * new MoveArm(clawSystem, -219),
     * new ExtendArm(clawSystem, 5),
     * new ExtendArm(clawSystem, 0),
     * new MoveArm(clawSystem, -1095),
     * new ExtendArm(clawSystem, 20),
     * new WaitCommand(1),
     * new ChangeClawStatus(clawSystem, ClawStatus.OPEN),
     * new WaitCommand(0.5),
     * new ExtendArm(clawSystem, 0),
     * new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
     * new BalanceAuto(drivetrain, -0.1).withTimeout(3),
     * new BalanceAutoPartTwo(drivetrain, -0.2).withTimeout(2));
     * }
     * // } else if (mode == 2) {
     */

    public void testAuto() {
        addCommands(new BalanceAutoPartTwo(drivetrain, -0.2));
    }

    public void edgeHighGoal() {

        // Mode 2 for going to charging station after
        addCommands(
                new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
                new MoveArm(clawSystem, -519),
                new ExtendArm(clawSystem, 5),
                new ExtendArm(clawSystem, 0),
                new Drive(drivetrain, 1, -0.2).withTimeout(0.7),
                new ExtendArm(clawSystem, 48),
                new WaitCommand(0.5),
                new MoveArm(clawSystem, -1250),
                new Drive(drivetrain, 1, 0.2).withTimeout(0.7),
                new WaitCommand(1),
                new ChangeClawStatus(clawSystem, ClawStatus.OPEN),
                new WaitCommand(0.5),
                new ExtendArm(clawSystem, 0),
                new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
                new BalanceAuto(drivetrain, -0.35).withTimeout(3));

        /*
         * addCommands(
         * new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
         * new MoveArm(clawSystem, -519),
         * new ExtendArm(clawSystem, 5),
         * new ExtendArm(clawSystem, 0),
         * new Drive(drivetrain, 1, -0.2).withTimeout(0.7),
         * new ExtendArm(clawSystem, 48),
         * new WaitCommand(0.5),
         * new MoveArm(clawSystem, -1250),
         * new Drive(drivetrain, 1, 0.2).withTimeout(0.7),
         * new WaitCommand(1),
         * new ChangeClawStatus(clawSystem, ClawStatus.OPEN),
         * new WaitCommand(0.5),
         * new ExtendArm(clawSystem, 0),
         * new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
         * new BalanceAuto(drivetrain, -0.1).withTimeout(3),
         * new BalanceAutoPartTwo(drivetrain, -0.2).withTimeout(2));
         */

    }

    public void ChargeStationMidGoal() {
        addCommands(
                new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
                new MoveArm(clawSystem, -500),
                new ExtendArm(clawSystem, 5),
                new ExtendArm(clawSystem, 0),
                new MoveArm(clawSystem, -1095),
                new ExtendArm(clawSystem, 20),
                new WaitCommand(1),
                new ChangeClawStatus(clawSystem, ClawStatus.OPEN),
                new WaitCommand(0.5),
                new ExtendArm(clawSystem, 0),
                new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
                new BalanceAuto(drivetrain, -0.4).withTimeout(6),
                new Drive(drivetrain, -.5, -.2).withTimeout(5),
                new BalanceAutoPartTwo(drivetrain, -0.15).withTimeout(5),
                new Drive(drivetrain, -.08, .2).withTimeout(5));
    }

}

// code for going for mid goal
// new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
// new MoveArm(clawSystem, -219),
// new ExtendArm(clawSystem, 5),
// new ExtendArm(clawSystem, 0),
// new MoveArm(clawSystem, -1095),
// new ExtendArm(clawSystem, 20),
// new WaitCommand(1),
// new ChangeClawStatus(clawSystem, ClawStatus.OPEN),
// new WaitCommand(0.5),
// new ExtendArm(clawSystem, 0),
// new ChangeClawStatus(clawSystem, ClawStatus.CLOSE),
// new BalanceAuto(drivetrain, -0.1).withTimeout(3),
// new BalanceAutoPartTwo(drivetrain, -0.2).withTimeout(2));
