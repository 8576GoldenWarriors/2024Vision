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
import frc.robot.Constants;

public class AprilTagStats extends SubsystemBase {

  //set cameras 
  PhotonCamera driverCam = new PhotonCamera("HD_Pro_Webcam_C920");
  PhotonCamera aprilTagCam = new PhotonCamera("Microsoft_LifeCam_HD-3000");

  public AprilTagStats() {
    driverCam.getDriverMode();
  }

  public void getStats() {
    PhotonPipelineResult backResult = aprilTagCam.getLatestResult();
    
    // checks if camera sees a april tag
    if (backResult.hasTargets()) { 
      // gets closest april tag that is visible
      PhotonTrackedTarget target = backResult.getBestTarget(); 
      
      // get data from camera
      int ID = target.getFiducialId();
      double yaw = target.getYaw();
      double pitch = target.getPitch();
      Transform3d camToTarget = target.getBestCameraToTarget();
      
      // push to smart dashboard
      SmartDashboard.putNumber("Back Cam Yaw", yaw);
      SmartDashboard.putNumber("Back Cam Pitch", pitch);
      SmartDashboard.putNumber("x Coordinate From Back", camToTarget.getX());
      SmartDashboard.putNumber("y Coordinate From Back", camToTarget.getY());
      SmartDashboard.putNumber("z Coordinate From Back", camToTarget.getZ());
      SmartDashboard.putNumber("ID Number", ID);
    }
  }

  public double getYaw() {
    return aprilTagCam.getLatestResult().getBestTarget().getYaw();
  }
  
  public double getPitch() {
    return aprilTagCam.getLatestResult().getBestTarget().getPitch();
  }
  
  public double[] getXYZ() {
    return new double[] {aprilTagCam.getLatestResult().getBestTarget().getBestCameraToTarget().getX(),
                         aprilTagCam.getLatestResult().getBestTarget().getBestCameraToTarget().getY(),
                         aprilTagCam.getLatestResult().getBestTarget().getBestCameraToTarget().getZ()};
  }
  
  public int getID() {
    return aprilTagCam.getLatestResult().getBestTarget().getFiducialId();
  }

  public boolean isIdealYaw() {
    return getYaw() > Constants.ledConstants.idealAprilTagYawRangeLow &&
           getYaw() < Constants.ledConstants.idealAprilTagYawRangeHigh;
  }

  public boolean isIdealDistance() {
    return Robot.ats.getXYZ()[2] > Constants.ledConstants.idealAprilTagZDistanceLow &&
           Robot.ats.getXYZ()[2] < Constants.ledConstants.idealAprilTagZDistanceHigh;
  }
}
