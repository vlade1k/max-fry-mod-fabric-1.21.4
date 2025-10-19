package ru.vlade1k.maxfrymod.client;

public class GuiUtil {
  public static final int DEFAULT_HEIGHT = 600;
  public static final int DEFAULT_WIDTH = 800;

  public static double getXCoordToRenderInCenter(int screenWidth, int widgetWidth) {
    return screenWidth / 2d - widgetWidth / 2d;
  }

  public static double getYCoordToRenderInCenter(int screenHeight, int widgetHeight) {
    return screenHeight / 2d + widgetHeight / 2d;
  }

  public static double getXCoordToRenderInCenter(int widgetWidth) {
    return (DEFAULT_WIDTH / 2d) - (widgetWidth / 2d);
  }

  public static double getYCoordToRenderInCenter(int widgetHeight) {
    return (DEFAULT_HEIGHT / 2d) - (widgetHeight / 2d);
  }
}
