class User {
  private static String GUI_USER = "None";
  public static String getUser() {
    return GUI_USER;
  }

  public static void setUser(String name) {
    GUI_USER = name;
  }
}
