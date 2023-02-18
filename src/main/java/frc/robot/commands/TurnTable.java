// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSystem;
import java.time.LocalTime;

public class TurnTable extends CommandBase {

    ClawSystem clawSystem;
    private double speed;
    private double setpoint;

    public TurnTable(ClawSystem clawSystem, double setpoint, double speed) {
        addRequirements(clawSystem);
        this.setpoint = setpoint;
        this.speed = speed;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Table started turning");

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        clawSystem.spinTable(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println("Table stopped turning");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
