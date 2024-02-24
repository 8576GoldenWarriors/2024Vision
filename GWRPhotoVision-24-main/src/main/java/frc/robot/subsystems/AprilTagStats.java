// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AprilTagStats extends SubsystemBase {

  PhotonCamera frontLogi = new PhotonCamera("Microsoft_LifeCam_HD-3000");
  PhotonCamera backLogi = new PhotonCamera("HD_Pro_Webcam_C920");

  public AprilTagStats() {}

  public void periodic() {} // torturous method, not even the KGB approves

  public void getStats() {
    PhotonPipelineResult frontResult = frontLogi.getLatestResult();
    PhotonPipelineResult backResult = backLogi.getLatestResult();
    if (frontResult.hasTargets()) { // does the image have any viable targets?
      PhotonTrackedTarget target = frontResult.getBestTarget(); // if it does, get the most identifiable target
      // get the stats from it
      double yaw = target.getYaw();
      double pitch = target.getPitch();
      Transform3d camToTarget = target.getBestCameraToTarget();
      double[] xyz = {camToTarget.getX(), camToTarget.getY(), camToTarget.getZ()};
      // print it out
      SmartDashboard.putNumber("Front Cam Yaw", yaw);
      SmartDashboard.putNumber("Front Cam Pitch", pitch);
      SmartDashboard.putNumberArray("xyz Coordinates From Front", xyz);
      System.out.println("this works very well!");
    }
    if (backResult.hasTargets()) { // does the image have any viable targets?
      PhotonTrackedTarget target = backResult.getBestTarget(); // if it does, get the most identifiable target
      // get the stats from it
      double yaw = target.getYaw();
      double pitch = target.getPitch();
      Transform3d camToTarget = target.getBestCameraToTarget();
      double[] xyz = {camToTarget.getX(), camToTarget.getY(), camToTarget.getZ()};
      // print it out
      SmartDashboard.putNumber("Back Cam Yaw", yaw);
      SmartDashboard.putNumber("Back Cam Pitch", pitch);
      SmartDashboard.putNumberArray("xyz Coordinates From Back", xyz);
    }
  }
}
