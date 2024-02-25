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

  PhotonCamera driverCam = new PhotonCamera("HD_Pro_Webcam_C920");
  PhotonCamera aprilTagCam = new PhotonCamera("Microsoft_LifeCam_HD-3000");

  public AprilTagStats() {
    driverCam.getDriverMode();
  }

  public void periodic() {} // torturous method, not even the KGB approves

  public void getStats() {
    PhotonPipelineResult backResult = aprilTagCam.getLatestResult();
    
    if (backResult.hasTargets()) { // does the image have any viable targets?
      PhotonTrackedTarget target = backResult.getBestTarget(); // if it does, get the most identifiable target
      // get the stats from it
      int ID = target.getFiducialId();
      double yaw = target.getYaw();
      double pitch = target.getPitch();
      Transform3d camToTarget = target.getBestCameraToTarget();
      // add it to the dashboard
      SmartDashboard.putNumber("Back Cam Yaw", yaw);
      SmartDashboard.putNumber("Back Cam Pitch", pitch);
      SmartDashboard.putNumber("x Coordinate From Back", camToTarget.getX());
      SmartDashboard.putNumber("y Coordinate From Back", camToTarget.getY());
      SmartDashboard.putNumber("z Coordinate From Back", camToTarget.getZ());
      SmartDashboard.putNumber("ID Number", ID);
    }
  }

  public double getYaw() {return aprilTagCam.getLatestResult().getBestTarget().getYaw();}
  public double getPitch() {return aprilTagCam.getLatestResult().getBestTarget().getPitch();}
  public double[] getXYZ() {
    double[] xyz = {aprilTagCam.getLatestResult().getBestTarget().getBestCameraToTarget().getX(),
                    aprilTagCam.getLatestResult().getBestTarget().getBestCameraToTarget().getY(),
                    aprilTagCam.getLatestResult().getBestTarget().getBestCameraToTarget().getZ()};
    return xyz;
  }
  public int getID() {return aprilTagCam.getLatestResult().getBestTarget().getFiducialId();}
}
