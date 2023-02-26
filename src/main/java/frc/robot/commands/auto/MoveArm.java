// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSystem;

public class MoveArm extends CommandBase {

    private ClawSystem clawSystem;
    private double setpoint;

    public MoveArm(ClawSystem clawSystem, double setpoint) {
        addRequirements(clawSystem);
        this.clawSystem = clawSystem;
        this.setpoint = MathUtil.clamp(setpoint, -50, 1182);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        clawSystem.setArmSetPoint(setpoint);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // We might want to use clamp to check if the current position is in approximate
        // range
        // since it might never equal to setpoint exactly

        return clawSystem.armAtSetpoint();
        // return false;

    }
}
