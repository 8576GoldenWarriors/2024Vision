// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class ledDisplay extends SubsystemBase {
  /** Creates a new ledDisplay. */
  AddressableLED addLED;
  static DigitalInput intakeInput;
  static DigitalInput transportInput;
  static AddressableLEDBuffer ledBuffer;
  public ledDisplay() 
  {
    addLED = new AddressableLED(Constants.ledConstants.addressableLEDPort);
    intakeInput = new DigitalInput(Constants.ledConstants.intakeInputChannel);
    transportInput = new DigitalInput(Constants.ledConstants.transportInputChannel);
    ledBuffer = new AddressableLEDBuffer(Constants.ledConstants.ledBufferLength);

    addLED.setLength(ledBuffer.getLength());
    addLED.setData(ledBuffer);
    addLED.start();
  }

  public static void shootingColor()
  {
    // Intake and transport indicative LEDs
    // if(!intakeInput.get() && !transportInput.get())
    // {
    //   for(int i = 0; i < ledBuffer.getLength(); i++)
    //   {
    //     ledBuffer.setRGB(i, 255, 0, 0);
    //   }
    // }
    // else if(transportInput.get())
    // {
    //   for(int i = 0; i < ledBuffer.getLength(); i++)
    //   {
    //     ledBuffer.setRGB(i, 255, 255, 0);
    //   }
    // }
    // else if(intakeInput.get())
    // {
    //   for(int i = 0; i < ledBuffer.getLength(); i++)
    //   {
    //     ledBuffer.setRGB(i, 0, 255, 0);
    //   }
    // }

    boolean isIdealYaw = Robot.ats.getYaw() > Constants.ledConstants.idealAprilTagYawRangeLow && Robot.ats.getYaw() < Constants.ledConstants.idealAprilTagYawRangeHigh;
    boolean isIdealDistance = Robot.ats.getXYZ()[2] > Constants.ledConstants.idealAprilTagZDistanceLow && Robot.ats.getXYZ()[2] < Constants.ledConstants.idealAprilTagZDistanceHigh;
    //If april tag is present - change the 0 to the april tag under speaker
    if(Robot.ats.hasTarget() && Robot.ats.getID() == 0)
    {
      // if yaw is in range and distance(z) is in range
      if(isIdealDistance && isIdealYaw)
      {
        for(int i = 0; i < ledBuffer.getLength(); i++)
        {
          // set green
          ledBuffer.setRGB(i, 0, 255, 0);
        }
      }
      // if only distance is in range
      else if(isIdealDistance)
      {
        for(int i = 0; i < ledBuffer.getLength(); i++)
        {
          // set orange
          ledBuffer.setRGB(i, 255, 165, 0);
        }
      }
      // if only yaw is in range
      else if(isIdealYaw)
      {
        for(int i = 0; i < ledBuffer.getLength(); i++)
        {
          // set yellow
          ledBuffer.setRGB(i, 255, 255, 0);
        }
      }
      // if neither
      else
      {
        for(int i = 0; i < ledBuffer.getLength(); i++)
        {
          // set red
          ledBuffer.setRGB(i, 255, 0, 0);
        }
      }
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
