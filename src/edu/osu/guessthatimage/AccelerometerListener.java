package edu.osu.guessthatimage;
// Source: http://androidexample.com/Accelerometer_Basic_Example_-_Detect_Phone_Shake_Motion/index.php?view=article_discription&aid=109&aaid=131
public interface AccelerometerListener {
    
    public void onAccelerationChanged(float x, float y, float z);
  
    public void onShake(float force);
  
}