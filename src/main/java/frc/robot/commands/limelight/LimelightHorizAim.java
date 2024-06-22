package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.Constants;
import frc.robot.subsystems.ClawSystem;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;

public class LimelightHorizAim extends Command {
    private ClawSystem clawSystem;
    private Limelight limelight;
    private XboxController secondaryController;
    private Drivetrain drivetrain;
    private final PIDController turnPid;

    public LimelightHorizAim(ClawSystem clawSystem, Limelight limelight,
            XboxController secondaryController, Drivetrain drivetrain) {

        this.clawSystem = clawSystem;
        this.limelight = limelight;
        this.secondaryController = secondaryController;
        this.drivetrain = drivetrain;
        turnPid = new PIDController(0.07, 0, 0.07 * 0.02);

    }

    @Override
    public void initialize() {
        // clawSystem.setTurretMode(ClawSystem.TurretMode.Power);
    }

    @Override
    public void end(boolean interrupted) {
        // clawSystem.setTurretMode(ClawSystem.TurretMode.Pid);
    }

    @Override
    public void execute() {
        if (limelight.isTargetDetected()) {
            double rotOut = turnPid.calculate(limelight.getXOffset());

            rotOut = MathUtil.clamp(rotOut, -1, 1);
            // clawSystem.spinTable(-rotOut);
            clawSystem.spinTablePID(clawSystem.getSpinTableSetpoint() + -rotOut * 8);

            System.out.println(
                    rotOut + " limelight x " + limelight.getXOffset() + " " + clawSystem.getSpinTableSetpoint());
            // System.out.println("L");
            // alt idea:
            // sp = clawSystem.getTurnSetPoint();
            // clawSystem.setTurnSetPoint(ap+rotOut);
            // should set the turntable setpoint to the displacement of the target
        } else {
            System.out.println("No target");
        }
    }

}