// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;

public class Drivetrain extends SubsystemBase {
    /** Creates a new Drivetrain. */
    // motor setup
    private final WPI_TalonFX leftFront = new WPI_TalonFX(3);
    private final WPI_TalonFX rightRear = new WPI_TalonFX(2);
    private final WPI_TalonFX leftRear = new WPI_TalonFX(5);
    private final WPI_TalonFX rightFront = new WPI_TalonFX(4);

    private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftFront, leftRear);
    private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightFront, rightRear);

    // Encoder encoder = new Encoder(0,1);

    Translation2d m_frontLeftLocation = new Translation2d(0.305, 0.257175);
    Translation2d m_frontRightLocation = new Translation2d(-0.305, 0.257175);
    Translation2d m_backLeftLocation = new Translation2d(0.305, -0.257175);
    Translation2d m_backRightLocation = new Translation2d(-0.305, -0.257175);

    private final Field2d m_feild = new Field2d();

    SlewRateLimiter filter = new SlewRateLimiter(1);
    SlewRateLimiter filterTurn = new SlewRateLimiter(1);

    // pretend that there is a second controller

    private final SlewRateLimiter speedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter rotLimiter = new SlewRateLimiter(3);

    // Odometry stuffs
    public static final double kGearRatio = 10.71;
    public static final double kWheelRadius = 0.076;
    public static final double kEncoderResolution = 2048;
    public static final double kDistancePerTick = (2 * Math.PI * kWheelRadius / kEncoderResolution) / kGearRatio;

    private final MecanumDriveKinematics kinematics = new MecanumDriveKinematics(m_backLeftLocation,
            m_backRightLocation, m_frontLeftLocation, m_frontRightLocation);
    private final MecanumDrive drivetrain = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);
    private AHRS navx;
    private Rotation2d initAngle;
    private final MecanumDriveOdometry odometry;
    double xCalc;
    double rotCalc;

    public Drivetrain() {

        SmartDashboard.putData("Field", m_feild);

        try {
            navx = new AHRS(Port.kMXP);
            // navx.setAngleAdjustment(navx.getRotation2d().getDegrees());
            initAngle = navx.getRotation2d();
            Shuffleboard.getTab("Debug").addDouble("NAVX", () -> navx.getRotation2d().minus(initAngle).getDegrees());
            Shuffleboard.getTab("Debug").addDouble("NAVX Roll", () -> navx.getRoll());

        } catch (Exception ex) {
        }

        odometry = new MecanumDriveOdometry(kinematics, navx.getRotation2d(),
                new MecanumDriveWheelPositions(getFLDistance(), getFRDistance(), getRLDistance(), getRRDistance()),
                new Pose2d(2.0, 3.03, new Rotation2d()));
        rightRear.setInverted(true);
        rightFront.setInverted(true);

        // Setup for each wheel motor
        WPI_TalonFX motors[] = { leftFront, leftRear, rightFront, rightRear };
        for (WPI_TalonFX wheelMotor : motors) {
            wheelMotor.configNominalOutputForward(0);
            wheelMotor.configNominalOutputReverse(0);
            wheelMotor.configFactoryDefault();
            wheelMotor.setNeutralMode(NeutralMode.Brake);
        }

    }

    public void driveFieldOriented(double x, double y, double rotation) {
        drivetrain.driveCartesian(x, y, rotation, navx.getRotation2d().minus(initAngle));
    }

    public void driveMecanum(double x, double y, double rotation) {
        driveMecanum(x, y, rotation, false);
    }

    public void driveMecanum(double x, double y, double rotation, boolean square) {
        if (square) {
            x = Math.copySign(Math.pow(x, 2), x);
            rotation = Math.copySign(Math.pow(rotation, 2), rotation);
            xCalc = speedLimiter.calculate(x);
            rotCalc = rotLimiter.calculate(rotation);
            drivetrain.driveCartesian(xCalc, y, rotCalc);
        } else {
            x = speedLimiter.calculate(x);
            rotation = rotLimiter.calculate(rotation);
            drivetrain.driveCartesian(x, y, rotation);
        }

    }

    public void zero() {
        initAngle = navx.getRotation2d();
    }

    public void zeroSensors() {
        // driveOdometry.resetPosition(new Pose2d(0.0, 0.0, new Rotation2d()),
        // Rotation2d.fromDegrees(navx.getYaw()));
        zero();
        leftFront.setSelectedSensorPosition(0);
        rightFront.setSelectedSensorPosition(0);
        leftRear.setSelectedSensorPosition(0);
        rightRear.setSelectedSensorPosition(0);
    }

    public Rotation2d getRotation() {
        return navx.getRotation2d().minus(initAngle);
    }

    public double getDistance() {
        double leftMotor = getFLDistance();
        double rightMotor = getFRDistance();
        double averageMove = (leftMotor + rightMotor) / 2;

        return averageMove;

    }

    public double getRRDistance() {
        double distance = rightRear.getSelectedSensorPosition() * kDistancePerTick;
        return distance;
    }

    public double getRLDistance() {
        double distance = leftRear.getSelectedSensorPosition() * kDistancePerTick;
        return distance;
    }

    public double getFRDistance() {
        double distance = rightFront.getSelectedSensorPosition() * kDistancePerTick;
        return distance;
    }

    public double getFLDistance() {
        double distance = leftFront.getSelectedSensorPosition() * kDistancePerTick;
        return distance;
    }

    public void periodic() {
        // System.out.println(navx.getRoll());
        // System.out.println("Are we climbing?: " + (navx.getRoll() > 10));
        // System.out.println("Are we level?: " + (navx.getRoll() < 0.4));
        // System.out.println("Front Left distance: " + getFLDistance());
        // System.out.println("Front Right distance: " + getFRDistance());

        var wheelPositions = new MecanumDriveWheelPositions(getFLDistance(),
                getFRDistance(), getRLDistance(), getRRDistance());

        var gyroAngle = navx.getRotation2d();

        odometry.update(gyroAngle, wheelPositions);

        m_feild.setRobotPose(odometry.getPoseMeters());
    }

    // really wished this was in its own subsystem but oh well
    public double getRoll() {
        return navx.getRoll();
    }

   
    // public void drive(double x, double y, double z) {
    // drivetrain.driveCartesian(-x, y, z);
    // }
}